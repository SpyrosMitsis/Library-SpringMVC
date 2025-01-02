package org.library.library.service.impl;

import org.library.library.dto.BookLoanDto;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.dto.CategoryLoanSummaryDto;
import org.library.library.model.*;
import org.library.library.repository.AppUserRepository;
import org.library.library.repository.BookInventoryRepository;
import org.library.library.repository.BookLoanRepository;
import org.library.library.repository.BookRepository;
import org.library.library.security.SecurityUtil;
import org.library.library.service.BookLoanService;
import org.library.library.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final AppUserRepository appUserRepository;

    public BookLoanServiceImpl(BookLoanRepository bookLoanRepository, BookInventoryRepository bookInventoryRepository, NotificationService notificationService, AppUserRepository appUserRepository, BookRepository bookRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public BookLoan borrowBook(String isbn) {

        AppUser borrower = appUserRepository.findByUsername(SecurityUtil.getCurrentUsername());
        BookInventory inventory = bookInventoryRepository.findByBookIsbn(isbn)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No book found with ISBN: " + isbn));


        if (hasOverdueBooks(borrower)) {
            throw new IllegalStateException("Cannot borrow new books while having overdue items");
        }

        if (inventory.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("No copies available for borrowing");
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - 1);
        bookInventoryRepository.save(inventory);

        // Create loan record
        BookLoan loan = BookLoan.builder()
                .book(inventory.getBook())
                .borrower(borrower)
                .borrowedAt(new Date(System.currentTimeMillis()))
                .dueDate(new Date(System.currentTimeMillis() + 2592000000L)) // 30 days in milliseconds
                .status(LoanStatus.ACTIVE)
                .build();

        return bookLoanRepository.save(loan);
    }

    @Override
    public boolean hasOverdueBooks(AppUser borrower) {
        return !bookLoanRepository.findByBorrowerAndStatus(borrower, LoanStatus.OVERDUE).isEmpty();
        }

    @Override
    public BookLoan returnBook(Long loanId) {
        BookLoan loan = bookLoanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalStateException("Book loan not found"));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new IllegalStateException("Book already returned");
        }

        BookInventory inventory = bookInventoryRepository.findByBookIsbn(loan.getBook().getIsbn())
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Book inventory not found"));
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + 1);
        bookInventoryRepository.save(inventory);

        loan.setReturnedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        loan.setStatus(LoanStatus.RETURNED);

        return bookLoanRepository.save(loan);
    }

    @Override
    public List<BookLoan> getActiveLoans() {
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        return bookLoanRepository.findByBorrowerAndStatus(user, LoanStatus.ACTIVE);
    }

    @Override
    public Page<BookLoan> getPersonalLoansByStatusPaginated(String status, PageRequest pageRequest) {
        LoanStatus loanStatus = LoanStatus.valueOf(status.toUpperCase());
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        return bookLoanRepository.findByBorrowerAndStatus(user, loanStatus, pageRequest);
    }

    @Override
    public List<BookLoan> getPersonalLoansByStatus(String status)  {
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        return bookLoanRepository.findByBorrowerAndStatus(user, LoanStatus.valueOf(status.toUpperCase()));
    }

    @Override
    public Page<BookLoan> getAllPersonalLoansPaginated(PageRequest pageRequest) {
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        return bookLoanRepository.findByBorrowerAndStatusIn(user, List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE, LoanStatus.RETURNED), pageRequest);

    }


    @Override
    public Page<BookLoan> getAllLoansPaginated(
            Pageable pageable,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate
    ) {
        return bookLoanRepository.findByBorrowedAtBetween(startDate, endDate, pageable);
    }
    public Map<String, Long> getBookLoansGroupedByMonth() {
        List<BookLoan> bookLoans = bookLoanRepository.findAll();

        return bookLoans.stream()
                .filter(loan -> loan.getBorrowedAt() != null)
                .collect(Collectors.groupingBy(
                        loan -> {
                            LocalDate borrowedAt = loan.getBorrowedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            return borrowedAt.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                        },
                        TreeMap::new,
                        Collectors.counting()
                ));
    }

    @Override
    public List<BookLoanSummaryDto> getTopNMostLoanedBooks(LoanStatus loanStatus, int n, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Pageable pageable= PageRequest.of(0, n);
        return bookLoanRepository.findMostLoanedBooks(
                Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                pageable
        );
    }

    @Override
    public List<BookLoanSummaryDto> getTopNMostLoanedBooks(LoanStatus loanStatus, int n) {
        Pageable pageable= PageRequest.of(0, n);
        LocalDateTime startDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.now();

        return bookLoanRepository.findMostLoanedBooks(
                Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                pageable
        );
    }

    @Override
    public List<CategoryLoanSummaryDto> findCategoryLoanSummary(Date startDate, Date endDate) {
        return bookLoanRepository.findCategoryLoanSummary(startDate, endDate);
    }
    @Override
    public List<CategoryLoanSummaryDto> findCategoryLoanSummary() {
        Date startDate = Date.from(LocalDateTime.of(1970, 1, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        return bookLoanRepository.findCategoryLoanSummary(startDate, endDate);
    }

    @Override
    public BookLoanDto getBookLoanByBookAndBorrower(Book book, AppUser borrower) {
        BookLoan loan = bookLoanRepository.findByBookAndBorrowerAndStatus(book, borrower, LoanStatus.ACTIVE);

        if (loan == null) {
            BookLoanDto loanDto = BookLoanDto.builder()
                    .isbn(null)
                    .isBorrowed(false)
                    .build();
            return loanDto;
        }
        else {
            BookLoanDto loanDto = BookLoanDto.builder()
                    .isbn(loan.getBook().getIsbn())
                    .isBorrowed(true)
                    .build();
            return loanDto;
        }
    }

    @Override
    public Page<BookLoan> findBooksByBookStartingWithTitlePaginated(String title, PageRequest pageRequest) {

        return bookLoanRepository.findByBookTitleContaining(title, pageRequest);
    }

    @Override
    public Page<BookLoan> findBooksByBorrowerAndStatusIn(AppUser borrower, List<LoanStatus> statuses, PageRequest pageRequest) {
        return bookLoanRepository.findByBorrowerAndStatusIn(borrower, statuses, pageRequest);
    }
}


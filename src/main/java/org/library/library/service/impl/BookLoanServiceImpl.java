package org.library.library.service.impl;

import org.library.library.model.*;
import org.library.library.repository.AppUserRepository;
import org.library.library.repository.BookInventoryRepository;
import org.library.library.repository.BookLoanRepository;
import org.library.library.security.SecurityUtil;
import org.library.library.service.BookLoanService;
import org.library.library.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final NotificationService notificationService;
    private final AppUserRepository appUserRepository;

    public BookLoanServiceImpl(BookLoanRepository bookLoanRepository, BookInventoryRepository bookInventoryRepository, NotificationService notificationService, AppUserRepository appUserRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.notificationService = notificationService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public BookLoan borrowBook(String isbn) {

        String username = SecurityUtil.getCurrentUsername();
        AppUser borrower = appUserRepository.findByUsername(username);
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

        // Update inventory
        BookInventory inventory = bookInventoryRepository.findByBookIsbn(loan.getBook().getIsbn())
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Book inventory not found"));
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + 1);
        bookInventoryRepository.save(inventory);

        loan.setReturnedAt(Date.from(Instant.from(LocalDateTime.now())));
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
    public Page<BookLoan> getActiveLoansPaginated(PageRequest pageRequest) {
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        return bookLoanRepository.findByBorrowerAndStatus(user, LoanStatus.ACTIVE, pageRequest);
    }

    @Override
    public List<BookLoan> getOverdueBookLoansPaginated(PageRequest pageRequest) {
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        Date date = Date.from(Instant.from(LocalDateTime.now()));
        return bookLoanRepository.findByStatusAndDueDateBefore(LoanStatus.OVERDUE, date, pageRequest).getContent();
    }

    @Override
    public void updateLoanStatuses() {
        Date now = Date.from(Instant.from(LocalDateTime.now()));
        List<BookLoan> activeLoans = bookLoanRepository.findByStatus(LoanStatus.ACTIVE);

        for (BookLoan loan : activeLoans) {
            if (loan.getDueDate().before(now) && loan.getStatus() != LoanStatus.OVERDUE) {
                loan.setStatus(LoanStatus.OVERDUE);
                bookLoanRepository.save(loan);
                notificationService.sendOverdueNotice(loan);
            }
        }
    }
}

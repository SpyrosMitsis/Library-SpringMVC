package org.library.library.service;

import org.library.library.dto.BookLoanDto;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.dto.CategoryLoanSummaryDto;
import org.library.library.model.AppUser;
import org.library.library.model.Book;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookLoanService {

    BookLoan borrowBook(String isbn );
    boolean hasOverdueBooks(AppUser borrower);
    BookLoan returnBook(Long loanId);
    List<BookLoan> getActiveLoans();
    Page<BookLoan> getPersonalLoansByStatusPaginated(String status, PageRequest pageRequest);
    List<BookLoan> getPersonalLoansByStatus(String status);

    Page<BookLoan> getAllPersonalLoansPaginated(PageRequest pageRequest);

    Page<BookLoan> getAllLoansPaginated(
            Pageable pageable,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate
    );
    Map<String, Long> getBookLoansGroupedByMonth();
    List<BookLoanSummaryDto> getTopNMostLoanedBooks(LoanStatus loanStatus, int n, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<BookLoanSummaryDto> getTopNMostLoanedBooks(LoanStatus loanStatus, int n);
    List<CategoryLoanSummaryDto> findCategoryLoanSummary(Date startDate, Date endDate);
    List<CategoryLoanSummaryDto> findCategoryLoanSummary();
    BookLoanDto getBookLoanByBookAndBorrower(Book book, AppUser borrower);
    Page<BookLoan> findBooksByBookStartingWithTitlePaginated(String title, PageRequest pageRequest);
    Page<BookLoan> findBooksByBorrowerAndStatusIn(AppUser borrower, List<LoanStatus> statuses, PageRequest pageRequest);
}

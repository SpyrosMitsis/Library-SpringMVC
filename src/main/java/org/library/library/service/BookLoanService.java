package org.library.library.service;

import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.model.AppUser;
import org.library.library.model.Book;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface BookLoanService {

    BookLoan borrowBook(String isbn );
    boolean hasOverdueBooks(AppUser borrower);
    BookLoan returnBook(Long loanId);
    List<BookLoan> getActiveLoans();
    Page<BookLoan> getActiveLoansPaginated(PageRequest pageRequest);

    List<BookLoan> getOverdueBookLoansPaginated(PageRequest pageRequest);

    Map<String, Long> getBookLoansGroupedByMonth();
    List<BookLoanSummaryDto> getTopNMostLoanedBooks(LoanStatus loanStatus, int n);

    void updateLoanStatuses();

}

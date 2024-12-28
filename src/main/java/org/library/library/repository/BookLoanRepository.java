package org.library.library.repository;

import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.model.AppUser;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

    List<BookLoan> findByBorrowerAndStatus(AppUser borrower, LoanStatus status);
    List<BookLoan> findByStatusAndDueDateBefore(LoanStatus status, Date date);

    Page<BookLoan> findByStatusAndDueDateBefore(LoanStatus status, Date date, PageRequest pageRequest);
    Page<BookLoan> findByBorrowerAndStatus(AppUser borrower, LoanStatus status, PageRequest pageRequest);

    List<BookLoan> findByStatus(LoanStatus loanStatus);

    @Query("""
            SELECT new org.library.library.dto.BookLoanSummaryDto(
                bl.book.isbn,
                bl.book.title,
                COUNT(bl.book.isbn) as loanCount
            )
            FROM BookLoan bl
            WHERE bl.borrowedAt BETWEEN :startDate AND :endDate
            GROUP BY bl.book.isbn, bl.book.title
            ORDER BY loanCount DESC
            """)
    List<BookLoanSummaryDto> findMostLoanedBooks(Date startDate, Date endDate, Pageable pageable);

}

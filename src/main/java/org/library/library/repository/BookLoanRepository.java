package org.library.library.repository;

import org.library.library.model.AppUser;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

    List<BookLoan> findByBorrowerAndStatus(AppUser borrower, LoanStatus status);
    List<BookLoan> findByStatusAndDueDateBefore(LoanStatus status, Date date);

    Page<BookLoan> findByStatusAndDueDateBefore(LoanStatus status, Date date, PageRequest pageRequest);
    Page<BookLoan> findByBorrowerAndStatus(AppUser borrower, LoanStatus status, PageRequest pageRequest);

    List<BookLoan> findByStatus(LoanStatus loanStatus);
}

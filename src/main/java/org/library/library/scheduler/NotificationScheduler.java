package org.library.library.scheduler;

import lombok.RequiredArgsConstructor;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.repository.BookLoanRepository;
import org.library.library.service.NotificationService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class NotificationScheduler {
    private final BookLoanRepository bookLoanRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkLoanDueDates() {

        Date today = Date.valueOf(LocalDate.now());
        Date tomorrow = Date.valueOf(LocalDate.now().plusDays(1));

        List<BookLoan> dueTomorrow = bookLoanRepository.findByStatusAndDueDateBefore(
                LoanStatus.ACTIVE,
                tomorrow
        );

        for (BookLoan loan : dueTomorrow) {
            String message = String.format("Your loan for '%s' expires tomorrow. Please return the book to avoid late fees.",
                    loan.getBook().getTitle());
            notificationService.createNotification(loan.getBorrower(), message);
        }

        // Check for expired loans
        List<BookLoan> expiredLoans = bookLoanRepository.findByStatusAndDueDateBefore(
                LoanStatus.ACTIVE,
                today
        );

        for (BookLoan loan : expiredLoans) {
            String message = String.format("OVERDUE: Your loan for '%s' has expired. Please return the book immediately!",
                    loan.getBook().getTitle());
            notificationService.createNotification(loan.getBorrower(), message);
        }
    }
}

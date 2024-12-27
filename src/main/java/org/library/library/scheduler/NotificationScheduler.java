package org.library.library.scheduler;

import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.repository.BookLoanRepository;
import org.library.library.service.BookLoanService;
import org.library.library.service.NotificationService;
import org.library.library.service.impl.BookLoanServiceImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class NotificationScheduler {
    private final BookLoanService bookLoanService;
    private BookLoanRepository bookLoanRepository;
    private NotificationService notificationService;

    public NotificationScheduler(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendDueDateReminders() {
        Date tomorrow = Date.from(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).atZone(java.time.ZoneId.systemDefault()).toInstant());
        List<BookLoan> dueTomorrow = bookLoanRepository.findByStatusAndDueDateBefore(
                LoanStatus.ACTIVE,
                tomorrow
        );
        for (BookLoan loan : dueTomorrow) {
            notificationService.sendDueDateReminder(loan);
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateLoanStatuses() {
        bookLoanService.updateLoanStatuses();
    }
}
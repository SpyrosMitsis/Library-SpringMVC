package org.library.library.scheduler;

import lombok.RequiredArgsConstructor;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.repository.BookLoanRepository;
import org.library.library.service.BookLoanService;
import org.library.library.service.NotificationService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

@Component
@EnableScheduling
public class NotificationScheduler {
    private final  NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "20 * * * * *")
    public void checkLoanDueDates() {
        notificationService.checkLoanDueDates();
        notificationService.updateOverdueLoans();
    }

}

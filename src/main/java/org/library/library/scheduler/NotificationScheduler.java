package org.library.library.scheduler;

import org.library.library.service.NotificationService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class NotificationScheduler {
    private final  NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkLoanDueDates() {
        notificationService.checkLoanDueDates();
        notificationService.updateOverdueLoans();
    }

}

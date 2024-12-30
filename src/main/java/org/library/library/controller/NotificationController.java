package org.library.library.controller;

import org.library.library.scheduler.NotificationScheduler;
import org.library.library.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private final NotificationScheduler notificationService;

    public NotificationController(NotificationScheduler notificationService1) {
        this.notificationService = notificationService1;
    }

    @GetMapping("/send-due-date-reminder")
    public void sendDueDateReminder() {
        notificationService.checkLoanDueDates();
    }
}

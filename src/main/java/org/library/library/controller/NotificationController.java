package org.library.library.controller;

import org.library.library.model.Notification;
import org.library.library.scheduler.NotificationScheduler;
import org.library.library.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/send-due-date-reminder")
    public List<Notification> sendDueDateReminder() {
        return notificationService.getUnreadNotifications();

    }
}

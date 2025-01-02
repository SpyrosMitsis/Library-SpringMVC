package org.library.library.controller;

import org.library.library.model.Notification;
import org.library.library.scheduler.NotificationScheduler;
import org.library.library.service.AppUserService;
import org.library.library.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final AppUserService appUserService;

    public NotificationController(NotificationService notificationService, AppUserService appUserService) {
        this.notificationService = notificationService;
        this.appUserService = appUserService;
    }

    @GetMapping("/send-due-date-reminder")
    public List<Notification> sendDueDateReminder() {
        return notificationService.getUnreadNotifications();

    }
    @PatchMapping("/mark-as-read")
    public ResponseEntity<Void> markAsRead(@RequestParam("notificationId") Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/all")
    public ModelAndView getAllNotificationsAdmin() {
        List<Notification> notifications = notificationService.getAllNotificationsByUser(appUserService.getAuthenticatedUser());

        ModelAndView modelAndView = new ModelAndView("admin/notification-list");
        modelAndView.addObject("notifications", notifications);

        return modelAndView;
    }
    @GetMapping("/library/all")
    public ModelAndView getAllNotificationsLibrary() {
        List<Notification> notifications = notificationService.getAllNotificationsByUser(appUserService.getAuthenticatedUser());

        ModelAndView modelAndView = new ModelAndView("library/notification-list");
        modelAndView.addObject("notifications", notifications);

        return modelAndView;
    }
}

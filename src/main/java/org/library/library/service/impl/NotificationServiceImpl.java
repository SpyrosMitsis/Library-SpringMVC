package org.library.library.service.impl;
import org.library.library.model.AppUser;
import org.library.library.model.BookLoan;
import org.library.library.model.Notification;
import org.library.library.repository.NotificationRepository;
import org.library.library.service.NotificationService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(AppUser user, String message) {
        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .isRead(false)
                .createdAt(Date.valueOf(LocalDate.now()))
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(AppUser user) {
        return notificationRepository.findByUserAndIsReadFalse(user);
    }
}

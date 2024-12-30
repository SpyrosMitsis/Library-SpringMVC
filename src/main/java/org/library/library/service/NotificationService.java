package org.library.library.service;

import org.library.library.model.AppUser;
import org.library.library.model.BookLoan;
import org.library.library.model.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(AppUser user, String message);
    void updateOverdueLoans();
    void checkLoanDueDates();
    List<Notification> getUnreadNotifications();
}

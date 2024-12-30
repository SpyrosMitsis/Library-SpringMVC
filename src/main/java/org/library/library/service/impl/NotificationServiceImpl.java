package org.library.library.service.impl;
import org.library.library.model.AppUser;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.model.Notification;
import org.library.library.repository.AppUserRepository;
import org.library.library.repository.BookLoanRepository;
import org.library.library.repository.NotificationRepository;
import org.library.library.security.SecurityUtil;
import org.library.library.service.AppUserService;
import org.library.library.service.NotificationService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookLoanRepository bookLoanRepository;
    private final AppUserService appUserService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, BookLoanRepository bookLoanRepository, AppUserService appUserService) {
        this.notificationRepository = notificationRepository;
        this.bookLoanRepository = bookLoanRepository;
        this.appUserService = appUserService;
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

    @Override
    public void updateOverdueLoans() {
        java.util.Date currentDate = new java.util.Date();
        List<BookLoan> overdueLoans = bookLoanRepository.findByStatusAndDueDateBefore(LoanStatus.ACTIVE, currentDate);

        overdueLoans.forEach(loan -> {
            loan.setStatus(LoanStatus.OVERDUE);
            bookLoanRepository.save(loan);
            createNotification(loan.getBorrower(), "OVERDUE: Book '" + loan.getBook().getTitle() + "' is overdue");
        });
    }

    @Override
    public void checkLoanDueDates() {
        java.util.Date tomorrow = java.sql.Date.valueOf(LocalDate.now().plusDays(1));

        List<BookLoan> dueTomorrow = bookLoanRepository.findByStatusAndDueDateBefore(
                LoanStatus.ACTIVE,
                tomorrow
        );

        for (BookLoan loan : dueTomorrow) {
            String message = String.format("Your loan for '%s' expires tomorrow. Please return the book to avoid late fees.",
                    loan.getBook().getTitle());
            createNotification(loan.getBorrower(), message);
        }

    }

    @Override
    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByUserAndIsReadFalse(appUserService.getAuthenticatedUser());
    }
}

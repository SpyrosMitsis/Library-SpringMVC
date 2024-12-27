package org.library.library.service;

import org.library.library.model.BookLoan;

public interface NotificationService {
    void sendDueDateReminder(BookLoan loan);
    void sendOverdueNotice(BookLoan loan);
}

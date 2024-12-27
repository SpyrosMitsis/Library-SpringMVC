package org.library.library.service.impl;

import org.library.library.model.BookLoan;
import org.library.library.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendDueDateReminder(BookLoan loan) {

    }

    @Override
    public void sendOverdueNotice(BookLoan loan) {

    }
}

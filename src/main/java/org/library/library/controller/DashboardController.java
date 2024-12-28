package org.library.library.controller;

import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.service.BookLoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    private final BookLoanService loanService;


    public DashboardController(BookLoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/dashboard")
    public String index(Model model) {
        List<BookLoanSummaryDto> activeLoans = loanService.getTopNMostLoanedBooks(LoanStatus.ACTIVE, 10);
        model.addAttribute("activeLoans", activeLoans);

        return "admin/dashboard";
    }

    @GetMapping("/line-chart")
    @ResponseBody
    public Map<String, Long> getBookLoanStats() {
        return loanService.getBookLoansGroupedByMonth();
    }
}

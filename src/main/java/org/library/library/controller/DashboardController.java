package org.library.library.controller;

import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.service.BookLoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.time.LocalDate;
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
    @GetMapping("loans/all")
    public String allLoans(Model model,
                           @RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "10") Integer size,
                           @RequestParam(value = "startDate", required = false) String startDate,
                           @RequestParam(value = "endDate", required = false) String endDate) {
        try {
            Date sqlStartDate = (startDate != null && !startDate.isEmpty())
                    ? Date.valueOf(startDate)
                    : Date.valueOf(LocalDate.now().minusYears(50));
            Date sqlEndDate = (endDate != null && !endDate.isEmpty())
                    ? Date.valueOf(endDate)
                    : Date.valueOf(LocalDate.now());

            Page<BookLoan> loanPage = loanService.getAllLoansPaginated(
                    PageRequest.of(page, size),
                    sqlStartDate,
                    sqlEndDate
            );

            model.addAttribute("loans", loanPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", loanPage.getTotalPages());
            model.addAttribute("size", size);

            return "admin/loan-list";
        } catch (IllegalArgumentException e) {

            model.addAttribute("errorMessage", "Invalid date format. Please use yyyy-MM-dd.");
            return "admin/loan-list";
        }
    }

}
package org.library.library.controller;

import org.library.library.model.BookLoan;
import org.library.library.security.CustomUserDetailsService;
import org.library.library.service.BookLoanService;
import org.library.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final BookLoanService loanService;

    public LoanController(BookLoanService loanService, BookService bookService, CustomUserDetailsService customUserDetailsService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow/{isbn}")
    public String borrowBook(@PathVariable String isbn) {
        loanService.borrowBook(isbn);
        return "redirect:/books/" + isbn + "?borrowed=true";
    }

    @PostMapping("/{loanId}/return")
    public String returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
        return "redirect:/loans/my?returned=true";
    }

    @GetMapping("/my")
    public String myLoans(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        Page<BookLoan> loanPage = loanService.getActiveLoansPaginated(PageRequest.of(page, size));

        List<BookLoan> activeLoans = loanService.getActiveLoans();
        System.out.println("Number of active loans: " + activeLoans.size());
        activeLoans.forEach(loan -> {
            System.out.println("Loan ID: " + loan.getId());
            System.out.println("Book Title: " + loan.getBook().getTitle());
            System.out.println("Book ISBN: " + loan.getBook().getIsbn());
            System.out.println("--------------------");
        });
        model.addAttribute("loans", loanPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", loanPage.getTotalPages());
        model.addAttribute("size", size);
        return "my-loans";
    }

}
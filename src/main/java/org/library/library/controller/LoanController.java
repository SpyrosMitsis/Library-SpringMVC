package org.library.library.controller;

import org.library.library.model.BookLoan;
import org.library.library.security.CustomUserDetailsService;
import org.library.library.service.BookLoanService;
import org.library.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final BookLoanService loanService;

    public LoanController(BookLoanService loanService, BookService bookService, CustomUserDetailsService customUserDetailsService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow/{isbn}")
    public String borrowBook(@PathVariable String isbn) {
        try {
            loanService.borrowBook(isbn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/books/" + isbn + "?borrowed=false&message=" + e.getMessage();
        }
        return "redirect:/books/" + isbn + "?borrowed=true";
    }

    @PostMapping("/{loanId}/return")
    public String returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
        return "redirect:/loans/my?returned=true";
    }

    @GetMapping("/my")
    public String myLoans(Model model,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) String query,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "9") int size) {

        Page<BookLoan> loanPage = loanService.getAllPersonalLoansPaginated(PageRequest.of(page, size));

        if (query != null) {
            loanPage = loanService.findBooksByBookStartingWithTitlePaginated(query, PageRequest.of(page, size));
        }
        if (status != null && !status.isEmpty()) {
            loanPage = loanService.getPersonalLoansByStatusPaginated(status, PageRequest.of(page, size));
        }

        model.addAttribute("loans", loanPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", loanPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("status", status);
        return "library/my-loans";
    }

}
package org.library.library.controller;

import org.library.library.model.AppUser;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.security.CustomUserDetailsService;
import org.library.library.service.AppUserService;
import org.library.library.service.BookLoanService;
import org.library.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertPathValidator;
import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final BookLoanService loanService;
    private final AppUserService appUserService;

    public LoanController(BookLoanService loanService, BookService bookService, CustomUserDetailsService customUserDetailsService, AppUserService appUserService) {
        this.loanService = loanService;
        this.appUserService = appUserService;
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

    @GetMapping("/librarian/{username}")
    public String getUserLoans(@PathVariable String username,
                               Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "9") int size
        ) {
        AppUser user = appUserService.findByUsername(username);
        Page<BookLoan> loans = loanService.findBooksByBorrowerAndStatusIn(user, List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE), PageRequest.of(page, size));

        model.addAttribute("loans", loans);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", loans.getTotalPages() - 1);
        return "library/user-loans-list";
    }

    @PostMapping("/librarian/{username}/return/{loanId}")
    public String extendLoan(@PathVariable Long loanId, @PathVariable String username) {
        loanService.returnBook(loanId);
        return "redirect:/loans/librarian/" + username + "?returned=true";
    }

}
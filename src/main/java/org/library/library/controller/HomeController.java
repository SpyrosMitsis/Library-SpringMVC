package org.library.library.controller;

import org.library.library.dto.BookListDto;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.dto.CategoryLoanSummaryDto;
import org.library.library.mapper.BookMapper;
import org.library.library.model.LoanStatus;
import org.library.library.service.BookLoanService;
import org.library.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final BookService bookService;
    private final BookLoanService bookLoanService;

    public HomeController(BookService bookService, BookLoanService bookLoanService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookLoanService = bookLoanService;
    }

    @GetMapping("/home")
    public String getAllBooks(Model model) {

        List<CategoryLoanSummaryDto> categoryLoanSummaryDtos = bookLoanService.findCategoryLoanSummary();
        List<BookListDto> books = bookService.getTopNMostLoanedBooks();

        model.addAttribute("books", books);


        model.addAttribute("categories", categoryLoanSummaryDtos);
        return "library/index";
    }
}

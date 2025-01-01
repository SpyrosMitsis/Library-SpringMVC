package org.library.library.controller;

import org.library.library.dto.BookListDto;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.dto.CategoryLoanSummaryDto;
import org.library.library.dto.RatingSummaryDto;
import org.library.library.mapper.BookMapper;
import org.library.library.model.LoanStatus;
import org.library.library.service.BookLoanService;
import org.library.library.service.BookService;
import org.library.library.service.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final BookService bookService;
    private final BookLoanService bookLoanService;
    private final RatingService ratingService;

    public HomeController(BookService bookService, BookLoanService bookLoanService, BookMapper bookMapper, RatingService ratingService) {
        this.bookService = bookService;
        this.bookLoanService = bookLoanService;
        this.ratingService = ratingService;
    }

    @GetMapping("/home")
    public String getAllBooks(Model model) {

        List<CategoryLoanSummaryDto> categoryLoanSummaryDtos = bookLoanService.findCategoryLoanSummary();
        List<BookListDto> mostLoanedBooks = bookService.getTopNMostLoanedBooks();
        List<BookListDto> highestRatedBooks = ratingService.getHighestRatedBooks();
        System.out.println("------------------------");
        System.out.println(highestRatedBooks);
        System.out.println("------------------------");

        model.addAttribute("categories", categoryLoanSummaryDtos);
        model.addAttribute("mostLoanedBooks", mostLoanedBooks);
        model.addAttribute("highestRatedBooks", highestRatedBooks);

        return "library/index";
    }
}

package org.library.library.controller;

import org.library.library.dto.RatingDto;
import org.library.library.security.SecurityUtil;
import org.library.library.service.AppUserService;
import org.library.library.service.BookService;
import org.library.library.service.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;

@Controller
public class RatingController {
    private final RatingService ratingService;
    private final BookService bookService;

    public RatingController(RatingService ratingService, BookService bookService) {
        this.ratingService = ratingService;
        this.bookService = bookService;
    }

    @PostMapping("/books/{isbn}/rate")
    public String rateBook(@ModelAttribute RatingDto ratingDTO,
                           @PathVariable String isbn,
                           @RequestParam float score) {

        ratingDTO.setUsername(SecurityUtil.getCurrentUsername());
        ratingDTO.setIsbn(isbn);
        ratingDTO.setScore(score);

        System.out.println("ISBN: " + isbn);
        System.out.println("Score: " + score);

        ratingService.rateBook(ratingDTO);

        return "redirect:/books/" + isbn;
    }

    @GetMapping("/books/{isbn}/rate")
    public String showRatingForm(@PathVariable String isbn, Model model) {
        model.addAttribute("book", bookService.findByIsbn(isbn));
        return "library/make-rating"; // The view that contains the rating form
    }

}
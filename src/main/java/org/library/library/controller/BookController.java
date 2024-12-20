package org.library.library.controller;
import org.library.library.model.Book;
import org.library.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        System.out.println("Hello from /books");
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books); // Pass books to the view
        return "index";
    }
}

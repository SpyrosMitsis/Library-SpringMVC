package org.library.library.controller;
import org.library.library.dto.BookListDto;
import org.library.library.model.Book;
import org.library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<BookListDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/book/{bookId}")
    public String getAllBooks(@PathVariable String bookId, Model model) {
        Book book = bookService.findByIsbn(bookId);
        model.addAttribute("book", book);
        return "book-detail";
    }
}

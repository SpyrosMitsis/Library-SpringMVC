package org.library.library.controller;

import org.library.library.dto.BookListDto;
import org.library.library.dto.NewAuthorDto;
import org.library.library.model.Author;
import org.library.library.service.AuthorService;
import org.library.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "library/author-list";
    }
    @GetMapping("/authors/{authorId}")
    public String getAuthorById(@PathVariable Long authorId, Model model) {
        Author author = authorService.findById(authorId);
        model.addAttribute("author", author);
        List<BookListDto> books = bookService.findByAuthorId(authorId);
        model.addAttribute("books", books);
        return "library/author-detail";
    }

    @GetMapping("admin/authors/add")
    public String showAddAuthorForm(Model model) {
        return "admin/add-author";
    }

    @PostMapping("admin/authors/add")
    public String addAuthor(@ModelAttribute NewAuthorDto authorDTO) {
        Author author = Author.builder()
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
                .build();
        author = authorService.save(author);
        return "redirect:/admin/authors/add?success";
    }
}
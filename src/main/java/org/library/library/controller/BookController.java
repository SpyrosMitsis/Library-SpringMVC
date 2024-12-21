package org.library.library.controller;
import org.library.library.dto.BookDto;
import org.library.library.dto.BookListDto;
import org.library.library.mapper.BookMapper;
import org.library.library.model.Author;
import org.library.library.model.Book;
import org.library.library.model.Category;
import org.library.library.service.AuthorService;
import org.library.library.service.BookService;
import org.library.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;


    @Autowired
    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<BookListDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/book/{bookId}")
    public String getAllBooks(@PathVariable String bookId, Model model) {
        Book book = bookService.findByIsbn(bookId);
        model.addAttribute("book", book);
        return "book-detail";
    }
    @GetMapping("/book/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "add-book";
    }

    @PostMapping("/book/add")
    public String addBook(@ModelAttribute BookDto bookDTO) {

        Book book = BookMapper.mapBookDtoToBook(bookDTO);

        // Add existing authors
        if (bookDTO.getAuthorIds() != null) {
            bookDTO.getAuthorIds().forEach(authorId -> {
                Author author = authorService.findById(authorId);
                book.addAuthor(author);
            });
        }

        // Create and add new authors
        if (bookDTO.getNewAuthors() != null) {
            bookDTO.getNewAuthors().forEach(newAuthorDTO -> {
                Author author = Author.builder()
                        .firstName(newAuthorDTO.getFirstName())
                        .lastName(newAuthorDTO.getLastName())
                        .build();

                author = authorService.save(author);
                book.addAuthor(author);
            });
        }

        // Add existing categories
        if (bookDTO.getCategoryIds() != null) {
            bookDTO.getCategoryIds().forEach(categoryId -> {
                Category category = categoryService.findById(categoryId);
                book.addCategory(category);
            });
        }

        // Create and add new categories
        if (bookDTO.getNewCategories() != null) {
            bookDTO.getNewCategories().forEach(newCategoryDTO -> {
                Category category = Category.builder()
                        .name(newCategoryDTO.getName())
                        .build();
                category = categoryService.save(category);
                book.addCategory(category);
            });
        }

        bookService.save(book);
        return "redirect:/book/add";
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
}

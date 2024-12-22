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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "index";
    }

    @GetMapping("/books-list")
    public String listBooks(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "9") int size,
                            Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Book> bookPage = bookService.findPaginated(pageRequest);

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("totalItems", bookPage.getTotalElements());

        return "book-list";
    }

    @GetMapping("/books/{bookId}")
    public String getAllBooks(@PathVariable String bookId, Model model) {
        Book book = bookService.findByIsbn(bookId);
        model.addAttribute("book", book);
        return "book-detail";
    }
    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "add-book";
    }

    @PostMapping("/books/add")
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
        return "redirect:/books/add";
    }
    @GetMapping("/")
    public String index() {
        return "dashboard";
    }
}

package org.library.library.controller;
import org.library.library.dto.BookDto;
import org.library.library.dto.BookListDto;
import org.library.library.dto.BookLoanDto;
import org.library.library.dto.RatingDto;
import org.library.library.mapper.BookMapper;
import org.library.library.model.*;
import org.library.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookLoanService bookLoanService;
    private final AppUserService appUserService;
    private final RatingService ratingService;


    @Autowired
    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService, BookLoanService bookLoanService, AppUserService appUserService, RatingService ratingService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookLoanService = bookLoanService;
        this.appUserService = appUserService;
        this.ratingService = ratingService;
    }



    @GetMapping("/books")
    public String listBooks(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "9") int size,
                            @RequestParam(required = false) Long categoryId,
                            @RequestParam(required = false) String query,
                            Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<BookListDto> books = bookService.findAllPaginated(pageRequest);

        System.out.println("----------------------");
        System.out.println( books.getContent());
        System.out.println("----------------------");

        if (categoryId != null) {
            books = bookService.findByCategoryIdPaginated(categoryId, pageRequest);
            Category category = categoryService.findById(categoryId);
            model.addAttribute("category", category);
        }
        if (query != null) {
            books = bookService.findByTitleContainingPaginated(query, pageRequest);
        }

        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("totalItems", books.getTotalElements());

        return "library/book-list";
    }

    @GetMapping("/books/{bookId}")
    public String getAllBooks(@PathVariable String bookId, Model model) {
        Book book = bookService.findByIsbn(bookId);
        BookLoanDto bookLoan = bookLoanService.getBookLoanByBookAndBorrower(book, appUserService.getAuthenticatedUser());
        List<RatingDto> ratings = ratingService.findByBookIsbn(bookId);
        float averageRating = ratingService.getAverageRating(bookId);

        model.addAttribute("averageRating", averageRating);
        model.addAttribute("book", book);
        model.addAttribute("bookLoan", bookLoan);
        model.addAttribute("ratings", ratings);
        return "library/book-detail";
    }
    @GetMapping("admin/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/add-book";
    }

    @PostMapping("admin/books/add")
    public String addBook(@ModelAttribute BookDto bookDTO) {
        Book book = BookMapper.mapBookDtoToBook(bookDTO);

        // Handle authors
        if (bookDTO.getAuthorIds() != null) {
            bookDTO.getAuthorIds().forEach(authorId -> {
                book.getAuthors().add(authorService.findById(authorId));
            });
        }

        if (bookDTO.getNewAuthors() != null) {
            bookDTO.getNewAuthors().forEach(newAuthorDTO -> {
                Author author = authorService.save(Author.builder()
                        .firstName(newAuthorDTO.getFirstName())
                        .lastName(newAuthorDTO.getLastName())
                        .build());
                book.getAuthors().add(author);
            });
        }

        // Handle categories
        if (bookDTO.getCategoryIds() != null) {
            bookDTO.getCategoryIds().forEach(categoryId -> {
                book.getCategories().add(categoryService.findById(categoryId));
            });
        }

        if (bookDTO.getNewCategories() != null) {
            bookDTO.getNewCategories().forEach(newCategoryDTO -> {
                Category category = categoryService.save(Category.builder()
                        .name(newCategoryDTO.getName())
                        .build());
                book.getCategories().add(category);
            });
        }

        // Save book first
        Book savedBook = bookService.save(book);

        // Then create and save inventory with saved book
        if (bookDTO.getTotalQuantity() != null) {
            System.out.println("Inventory data provided.");
            BookInventory inventory = BookInventory.builder()
                    .book(savedBook)
                    .totalQuantity(bookDTO.getTotalQuantity())
                    .availableQuantity(bookDTO.getTotalQuantity())
                    .build();
            bookService.saveInvetory(inventory);
        } else {
            System.out.println("No inventory data provided.");
        }

        return "redirect:/admin/books/add?success";
    }


}

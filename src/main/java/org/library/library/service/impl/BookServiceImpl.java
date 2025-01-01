package org.library.library.service.impl;

import org.library.library.dto.BookListDto;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.mapper.BookInventoryMapper;
import org.library.library.mapper.BookMapper;
import org.library.library.model.AppUser;
import org.library.library.model.Book;
import org.library.library.model.BookInventory;
import org.library.library.model.LoanStatus;
import org.library.library.repository.AppUserRepository;
import org.library.library.repository.BookInventoryRepository;
import org.library.library.repository.BookRepository;
import org.library.library.security.SecurityUtil;
import org.library.library.service.BookLoanService;
import org.library.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.library.library.mapper.BookMapper.mapToBookListDto;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AppUserRepository appUserRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final BookLoanService bookLoanService;

    public BookServiceImpl(BookRepository bookRepository, AppUserRepository appUserRepository, BookInventoryRepository bookInventoryRepository, BookLoanService bookLoanService) {
        this.bookRepository = bookRepository;
        this.appUserRepository = appUserRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookLoanService = bookLoanService;
    }

    @Override
    public List<BookListDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper::mapToBookListDto).collect(toList());
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book save(Book book) {
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        book.setCreatedBy(user);
        return bookRepository.save(book);

    }

    @Override
    public BookInventory saveInvetory(BookInventory bookInventory) {
        return bookInventoryRepository.save(bookInventory);
    }

    @Override
    public void update(Book book) {
        String username = SecurityUtil.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username);
        book.setCreatedBy(user);
        bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book addBook(Book book) {
        if (bookRepository.existsById(book.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        return bookRepository.save(book);
    }

    @Override
    public List<BookListDto> findByAuthorId(Long authorId) {
        List<Book> books = bookRepository.findBooksByAuthorsId(authorId);
        return books.stream().map(BookMapper::mapToBookListDto).collect(toList());
    }

    @Override
    public Page<BookListDto> findAllPaginated(PageRequest pageRequest) {
        Page<BookInventory> booksInvetory = bookInventoryRepository.findAll(pageRequest);

        Page<BookListDto> books = booksInvetory.map(BookInventoryMapper::mapToBookListDto);

        return books;


    }

    @Override
    public Page<BookListDto> findByCategoryIdPaginated(Long categoryId, PageRequest pageRequest) {
        Page<BookInventory> bookPage = bookInventoryRepository.findByBookCategoriesId(categoryId, pageRequest);
        return bookPage.map(BookInventoryMapper::mapToBookListDto);
    }

    @Override
    public Page<BookListDto> findByTitleContainingPaginated(String title, PageRequest pageRequest) {
        Page<BookInventory> bookPage = bookInventoryRepository.findByBookTitleContaining(title, pageRequest);
        return bookPage.map(BookInventoryMapper::mapToBookListDto);
    }

    @Override
    public List<BookListDto> getTopNMostLoanedBooks() {
        List<BookLoanSummaryDto> activeLoans = bookLoanService.getTopNMostLoanedBooks(LoanStatus.ACTIVE, 10);
        List<BookListDto> books = activeLoans.stream()
                .map(loan -> mapToBookListDto(findByIsbn(loan.getIsbn())))
                .collect(toList());

        return books;
    }
}

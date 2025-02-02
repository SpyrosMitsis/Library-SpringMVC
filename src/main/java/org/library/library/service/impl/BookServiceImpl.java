package org.library.library.service.impl;

import org.library.library.dto.AuthorDto;
import org.library.library.dto.BookListDto;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.mapper.BookInventoryMapper;
import org.library.library.mapper.BookMapper;
import org.library.library.mapper.SqlToBookListDtoMapper;
import org.library.library.model.*;
import org.library.library.repository.*;
import org.library.library.security.SecurityUtil;
import org.library.library.service.BookLoanService;
import org.library.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.library.library.mapper.BookMapper.mapToBookListDto;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AppUserRepository appUserRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final BookLoanService bookLoanService;
    private final BookSelectionRepository bookSelectionRepository;
    private final RatingRepository ratingRepository;

    public BookServiceImpl(BookRepository bookRepository, AppUserRepository appUserRepository, BookInventoryRepository bookInventoryRepository, BookLoanService bookLoanService, BookSelectionRepository bookSelectionRepository, RatingRepository ratingRepository) {
        this.bookRepository = bookRepository;
        this.appUserRepository = appUserRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookLoanService = bookLoanService;
        this.bookSelectionRepository = bookSelectionRepository;
        this.ratingRepository = ratingRepository;
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
        books.getContent().forEach(book -> {
            Float averageRating = ratingRepository.findAverageRatingByBookIsbn(book.getIsbn());
            book.setRating(averageRating != null ? averageRating : 0.0f);

        });

        return books;

    }

    @Override
    public Page<BookListDto> findByCategoryIdsPaginated(List<Long> categoryIds, PageRequest pageRequest) {
        Page<BookInventory> bookPage = bookInventoryRepository.findByBookCategoriesIn(categoryIds, pageRequest);
        return bookPage.map(BookInventoryMapper::mapToBookListDto);
    }

    @Override
    public Page<BookListDto> findByTitleOrIsbnContainingPaginated(String title, String isbn, PageRequest pageRequest) {
        Page<BookInventory> bookPage = bookInventoryRepository.findByBookTitleContainingOrBookIsbnContaining(title, isbn, pageRequest);
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
    @Override
    public List<BookListDto> getBookSelection(){
        List<BookSelection> bookSelection = bookSelectionRepository.findAll();
        List<Book> books = bookSelection.stream().map(BookSelection::getBook).toList();
        return books.stream().map(BookMapper::mapToBookListDto).toList();
    }
    @Override
    public Page<BookListDto> findByRatingAndCategoriesPaginated(Float rating, List<Long> categoryIds, PageRequest pageRequest) {
        Page<BookListDto> bookPage = bookInventoryRepository.findBooksByRatingsAndCategories(rating,categoryIds, pageRequest)
                .map(SqlToBookListDtoMapper::mapToBookListDto);

        return bookPage;
    }

    @Override
    public Page<BookListDto> findByRatingPaginated(Float minRating, PageRequest pageRequest) {
        Page<BookListDto> bookPage = bookInventoryRepository.findBooksByRatings(minRating, pageRequest)
                .map(SqlToBookListDtoMapper::mapToBookListDto);

        return bookPage;
    }
}


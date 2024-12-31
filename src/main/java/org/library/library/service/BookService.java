package org.library.library.service;
import org.library.library.dto.BookListDto;
import org.library.library.model.Book;
import org.library.library.model.BookInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookListDto> findAll();
    Book findByIsbn(String isbn);
    Book save(Book book);
    BookInventory saveInvetory(BookInventory bookInventory);
    void update(Book book);
    void delete(Book book);
    Book addBook(Book book);
    List<BookListDto> findByAuthorId(Long authorId);
    Page<Book> findAllPaginated(PageRequest pageRequest);
    Page<Book> findByCategoryIdPaginated(Long categoryId, PageRequest pageRequest);
    Page<Book> findByTitleContainingPaginated(String title, PageRequest pageRequest);
    List<BookListDto> getTopNMostLoanedBooks();
}
package org.library.library.service;
import org.library.library.dto.BookListDto;
import org.library.library.model.Book;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookService {
    List<BookListDto> findAll();
    Book findByIsbn(String isbn);
    Book save(Book book);
    void delete(Book book);
    Book addBook(Book book);
    List<BookListDto> findByAuthorId(Long authorId);
}
package org.library.library.service.impl;

import org.library.library.model.Book;
import org.library.library.repository.BookRepository;
import org.library.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);

    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

}

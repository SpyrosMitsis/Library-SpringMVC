package org.library.library.service;
import org.library.library.model.Book;
import java.util.List;

public interface BookService {
    List<Book> findAll();
}
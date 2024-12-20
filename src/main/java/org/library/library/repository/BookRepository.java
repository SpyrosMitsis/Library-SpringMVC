package org.library.library.repository;

import org.library.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
}

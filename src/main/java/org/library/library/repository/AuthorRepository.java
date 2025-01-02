package org.library.library.repository;

import org.library.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByBooksIsbn(String isbn);
}

package org.library.library.repository;

import org.library.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
    List<Book> findBooksByAuthorsId(Long authorId);

    Page<Book> findByCategoriesId(Long categoryId, Pageable pageable);
}

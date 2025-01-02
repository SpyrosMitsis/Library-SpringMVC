package org.library.library.repository;

import org.library.library.dto.CategoryDto;
import org.library.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByBooksIsbn(String isbn);
}

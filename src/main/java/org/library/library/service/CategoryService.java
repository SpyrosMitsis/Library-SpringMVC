package org.library.library.service;

import org.library.library.dto.CategoryDto;
import org.library.library.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);

    List<CategoryDto> findByBooksIsbn(String isbn);
}

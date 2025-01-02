package org.library.library.service.impl;

import org.library.library.dto.CategoryDto;
import org.library.library.model.Category;
import org.library.library.repository.CategoryRepository;
import org.library.library.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> findByBooksIsbn(String isbn) {
        List<Category> categories = categoryRepository.findByBooksIsbn(isbn);
        return categories.stream().map(category -> CategoryDto.builder()
                .name(category.getName())
                .build()
        ).toList();
    }
}

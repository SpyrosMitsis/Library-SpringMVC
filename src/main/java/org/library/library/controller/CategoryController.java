package org.library.library.controller;

import org.library.library.dto.CategoryDto;
import org.library.library.dto.CategoryLoanSummaryDto;
import org.library.library.dto.NewCategoryDto;
import org.library.library.mapper.CategoryMapper;
import org.library.library.model.Category;
import org.library.library.service.BookLoanService;
import org.library.library.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, BookLoanService bookLoanService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/admin/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/add-category";
    }
    @PostMapping("/admin/categories/add")
    public String addCategory(@ModelAttribute NewCategoryDto categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();

        categoryService.save(category);

        return "redirect:/admin/categories/add?success";
    }
    @GetMapping("/categories")
    public String showCategories(Model model) {
        List<CategoryDto> categories = categoryService.findAll()
                        .stream().map(CategoryMapper::mapToCategoryDto).toList();
        model.addAttribute("categories", categories);
        return "library/category-list";
    }

}

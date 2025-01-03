package org.library.library.mapper;

import org.library.library.dto.CategoryDto;
import org.library.library.model.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

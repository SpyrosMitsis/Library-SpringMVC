package org.library.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto{
    private String isbn;
    private String title;
    private String description;
    private String coverUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private Boolean isAvailable;
    private List<Long> authorIds;
    private List<Long> categoryIds;
    private List<NewAuthorDto> newAuthors;
    private List<NewCategoryDto> newCategories;
}

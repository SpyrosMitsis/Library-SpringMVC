package org.library.library.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.library.library.model.AppUser;
import org.library.library.model.Book;
import org.library.library.model.BookInventory;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto{
    private String isbn;
    private String title;
    private String description;
    private String coverUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private Boolean isAvailable;
    @OneToOne
    @JoinColumn(name = "book_isbn")
    private Integer totalQuantity;
    private List<Long> authorIds;
    private List<Long> categoryIds;
    private List<NewAuthorDto> newAuthors;
    private List<NewCategoryDto> newCategories;
    private AppUser createdBy;
}

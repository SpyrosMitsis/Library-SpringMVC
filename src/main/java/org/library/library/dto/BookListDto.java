package org.library.library.dto;

import lombok.Builder;
import lombok.Data;
import org.library.library.model.Author;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class BookListDto {
    private String isbn;
    private String title;
    private String coverUrl;
    private Date releaseDate;
    private Boolean isAvailable;
    private Set<Author> authors = new HashSet<>();
}

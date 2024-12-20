package org.library.library.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookListDto {
    private String isbn;
    private String title;
    private String smallCoverUrl;
    private Date releaseDate;
    private Boolean isAvailable;
}

package org.library.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingDto {
    private Long id;
    private float score;
    private String isbn;
    private String username;
}

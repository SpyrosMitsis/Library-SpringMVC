package org.library.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RatingSummaryDto {
    private Double averageScore;
    private Long ratingCount;
    private String isbn;
}

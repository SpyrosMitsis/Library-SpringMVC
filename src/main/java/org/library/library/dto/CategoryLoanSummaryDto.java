package org.library.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryLoanSummaryDto {
    private Long categoryId;
    private String categoryName;
    private Long loanCount;
}

package org.library.library.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.library.library.model.Author;

@Data
@AllArgsConstructor
public class BookLoanSummaryDto {
    private String isbn;
    private String title;
    private Long loanCount;
}

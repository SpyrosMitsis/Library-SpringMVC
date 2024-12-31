package org.library.library.dto;

import lombok.Builder;
import lombok.Data;
import org.library.library.model.Book;

@Data
@Builder
public class BookLoanDto {
    private String isbn;
    private boolean isBorrowed;
}

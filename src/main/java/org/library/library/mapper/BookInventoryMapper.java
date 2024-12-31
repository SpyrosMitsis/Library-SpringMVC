package org.library.library.mapper;

import org.library.library.dto.BookListDto;
import org.library.library.model.Book;
import org.library.library.model.BookInventory;

public class BookInventoryMapper {

    public static BookListDto mapToBookListDto(BookInventory bookInventory) {
        return BookListDto.builder()
                .isbn(bookInventory.getBook().getIsbn())
                .title(bookInventory.getBook().getTitle())
                .coverUrl(bookInventory.getBook().getCoverUrl())
                .releaseDate(bookInventory.getBook().getReleaseDate())
                .isAvailable(bookInventory.getBook().getIsAvailable())
                .authors(bookInventory.getBook().getAuthors())
                .build();
    }
}

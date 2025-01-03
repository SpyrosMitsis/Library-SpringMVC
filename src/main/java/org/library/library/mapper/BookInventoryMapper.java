package org.library.library.mapper;

import org.library.library.dto.BookListDto;
import org.library.library.dto.RatingDto;
import org.library.library.model.Book;
import org.library.library.model.BookInventory;
import org.library.library.model.Rating;

import java.util.stream.Collectors;

public class BookInventoryMapper {

    public static BookListDto mapToBookListDto(BookInventory bookInventory) {
        return BookListDto.builder()
                .isbn(bookInventory.getBook().getIsbn())
                .title(bookInventory.getBook().getTitle())
                .coverUrl(bookInventory.getBook().getCoverUrl())
                .releaseDate(bookInventory.getBook().getReleaseDate())
                .isAvailable(bookInventory.getBook().getIsAvailable())
                .authors(bookInventory.getBook().getAuthors().stream().map(AuthorMapper::mapToAuthorDto).collect(Collectors.toSet()))
                .build();
    }
}

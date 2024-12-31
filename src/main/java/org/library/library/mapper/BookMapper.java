package org.library.library.mapper;

import org.library.library.dto.BookDto;
import org.library.library.dto.BookListDto;
import org.library.library.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public static BookListDto mapToBookListDto(Book book) {
        return BookListDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .coverUrl(book.getCoverUrl())
                .releaseDate(book.getReleaseDate())
                .isAvailable(book.getIsAvailable())
                .authors(book.getAuthors())
                .createdBy(book.getCreatedBy())
                .build();
    }
    public static Book mapBookDtoToBook(BookDto bookListDto) {
        return Book.builder()
                .isbn(bookListDto.getIsbn())
                .title(bookListDto.getTitle())
                .description(bookListDto.getDescription())
                .coverUrl(bookListDto.getCoverUrl())
                .releaseDate(bookListDto.getReleaseDate())
                .isAvailable(bookListDto.getIsAvailable())
                .createdBy(bookListDto.getCreatedBy())
                .build();
    }
    public static BookDto mapToBookDto(Book book) {
        return BookDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .description(book.getDescription())
                .coverUrl(book.getCoverUrl())
                .releaseDate(book.getReleaseDate())
                .isAvailable(book.getIsAvailable())
                .createdBy(book.getCreatedBy())
                .build();
    }
}

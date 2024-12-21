package org.library.library.mapper;

import org.library.library.dto.BookDto;
import org.library.library.dto.BookListDto;
import org.library.library.model.Book;

public class BookMapper {
    public static BookListDto mapToBookListDto(Book book) {
        return BookListDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .smallCoverUrl(book.getSmallCoverUrl())
                .releaseDate(book.getReleaseDate())
                .isAvailable(book.getIsAvailable())
                .authors(book.getAuthors())
                .build();
    }
    public static Book mapBookDtoToBook(BookDto bookListDto) {
        return Book.builder()
                .isbn(bookListDto.getIsbn())
                .title(bookListDto.getTitle())
                .smallCoverUrl(bookListDto.getSmallCoverUrl())
                .bigCoverUrl(bookListDto.getBigCoverUrl())
                .releaseDate(bookListDto.getReleaseDate())
                .isAvailable(bookListDto.getIsAvailable())
                .build();
    }
}

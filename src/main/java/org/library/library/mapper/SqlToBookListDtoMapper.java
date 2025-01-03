package org.library.library.mapper;

import org.library.library.dto.AuthorDto;
import org.library.library.dto.BookListDto;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class SqlToBookListDtoMapper {
    public static BookListDto mapToBookListDto(Object[] row) {
        String isbn = (String) row[0];
        String title = (String) row[1];
        String coverUrl = (String) row[2];
        Date releaseDate = (Date) row[3];
        String authors = (String) row[5];
        Boolean isAvailable = (Boolean) row[4];
        Double rating = (Double) row[6];

        Set<AuthorDto> authorSet = Arrays.stream(authors.split(",")) // Split authors by comma
                .map(String::trim)
                .map(fullName -> {
                    String[] nameParts = fullName.split(" ", 2); // Split into first and last name
                    String firstName = nameParts[0];
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";
                    return AuthorDto.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .build(); // Build AuthorDto
                })
                .collect(Collectors.toSet());

        return BookListDto.builder()
                .isbn(isbn)
                .title(title)
                .coverUrl(coverUrl)
                .releaseDate(releaseDate)
                .isAvailable(isAvailable)
                .authors(authorSet)
                .rating(rating)
                .build();
    }
}

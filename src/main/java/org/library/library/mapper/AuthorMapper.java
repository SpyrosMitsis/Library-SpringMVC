package org.library.library.mapper;

import org.library.library.dto.AuthorDto;
import org.library.library.model.Author;

public class AuthorMapper {
    public static AuthorDto mapToAuthorDto(Author author) {
        AuthorDto authorDto = AuthorDto.builder()
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
        return authorDto;
    }
}

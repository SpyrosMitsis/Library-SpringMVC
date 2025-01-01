package org.library.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {
    private String firstName;
    private String lastName;
}
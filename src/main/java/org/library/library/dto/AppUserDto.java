package org.library.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserDto {
    private String username;
    private String firstName;
    private String lastName;
}

package org.library.library.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class RegistrationDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}

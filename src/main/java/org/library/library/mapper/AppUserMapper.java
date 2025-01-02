package org.library.library.mapper;

import org.library.library.dto.AppUserDto;
import org.library.library.model.AppUser;

public class AppUserMapper {
    public static AppUserDto mapToAppUserDto(AppUser appUser) {
        return AppUserDto.builder()
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .build();
    }
}

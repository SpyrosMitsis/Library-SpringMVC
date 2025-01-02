package org.library.library.service;

import org.library.library.dto.RegistrationDto;
import org.library.library.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AppUserService {
    void saveUser(RegistrationDto registrationDto);

    AppUser getAuthenticatedUser();
    AppUser findByUsername(String username);
    long countAllUsers();
    List<AppUser> getAllUsers();
}

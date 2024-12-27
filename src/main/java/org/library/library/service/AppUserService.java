package org.library.library.service;

import org.library.library.dto.RegistrationDto;
import org.library.library.model.AppUser;
import org.springframework.stereotype.Service;

public interface AppUserService {
    void saveUser(RegistrationDto registrationDto);

    AppUser findByUsername(String username);
}

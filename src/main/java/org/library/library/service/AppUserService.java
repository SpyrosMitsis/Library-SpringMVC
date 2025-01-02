package org.library.library.service;

import org.library.library.dto.RegistrationDto;
import org.library.library.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AppUserService {
    void saveUser(RegistrationDto registrationDto);

    AppUser getAuthenticatedUser();
    AppUser findByUsername(String username);
    long countAllUsers();
    Page<AppUser> getAllUsersPaginated(PageRequest pageRequest);

    Page<AppUser> getAllUsersByUsernamePaginated(@RequestParam("username") String username, PageRequest pageRequest);
}

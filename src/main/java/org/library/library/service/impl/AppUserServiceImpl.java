package org.library.library.service.impl;

import org.library.library.dto.RegistrationDto;
import org.library.library.model.AppUser;
import org.library.library.model.Role;
import org.library.library.repository.AppUserRepository;
import org.library.library.repository.RoleRepository;
import org.library.library.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        AppUser user = new AppUser();
        user.setUsername(registrationDto.getUsername());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPassword(registrationDto.getPassword());
        Role role = roleRepository.findByName("USER");
        user.setRoles(Set.of(role));
        appUserRepository.save(user);

    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}

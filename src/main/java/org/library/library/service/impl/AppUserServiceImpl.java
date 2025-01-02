package org.library.library.service.impl;

import org.library.library.dto.RegistrationDto;
import org.library.library.model.AppUser;
import org.library.library.model.Role;
import org.library.library.repository.AppUserRepository;
import org.library.library.repository.RoleRepository;
import org.library.library.security.SecurityUtil;
import org.library.library.service.AppUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        AppUser user = new AppUser();
        user.setUsername(registrationDto.getUsername());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        user.setRoles(Set.of(role));
        appUserRepository.save(user);

    }

    @Override
    public AppUser getAuthenticatedUser() {
        return appUserRepository.findByUsername(SecurityUtil.getCurrentUsername());
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public long countAllUsers() {
        return appUserRepository.count();
    }

    @Override
    public Page<AppUser> getAllUsersPaginated(PageRequest pageRequest) {
        return appUserRepository.findAll(pageRequest);
    }
    @Override
    public Page<AppUser> getAllUsersByUsernamePaginated(@RequestParam("username") String username, PageRequest pageRequest) {
        return appUserRepository.findAppUsersByUsernameContaining(username, pageRequest);
    }
}

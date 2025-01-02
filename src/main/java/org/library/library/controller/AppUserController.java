package org.library.library.controller;

import org.library.library.dto.AppUserDto;
import org.library.library.mapper.AppUserMapper;
import org.library.library.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("librarian")
public class AppUserController {
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @GetMapping("getAllUsers")
    public String getAllUsers(Model model) {
        List<AppUserDto> users = appUserService.getAllUsers().stream().map(AppUserMapper::mapToAppUserDto).toList();
        model.addAttribute("users", users);
        return "library/user-list";
    }
}

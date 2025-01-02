package org.library.library.controller;

import org.library.library.dto.AppUserDto;
import org.library.library.mapper.AppUserMapper;
import org.library.library.service.AppUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("librarian")
public class AppUserController {
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @GetMapping("getAllUsers")
    public String getAllUsers(
            Model model,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size

    ) {
        Page<AppUserDto> users = appUserService.getAllUsersPaginated(PageRequest.of(page, size)).map(AppUserMapper::mapToAppUserDto);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", users.getTotalPages() - 1);
        return "library/user-list";
    }
    @GetMapping("getUsers/{username}")
    public String getUsers(
            @PathVariable String username,
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Page<AppUserDto> users = appUserService.
                getAllUsersByUsernamePaginated(username, PageRequest.of(page, size))
                .map(AppUserMapper::mapToAppUserDto);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", users.getTotalPages() - 1);
        return "library/user-list";
    }
    @PostMapping("getUsers/search")
    public String searchUsers(
            @RequestParam String username,
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Page<AppUserDto> users = appUserService
                .getAllUsersByUsernamePaginated(username, PageRequest.of(page, size))
                .map(AppUserMapper::mapToAppUserDto);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", users.getTotalPages() - 1);
        return "redirect:/librarian/getUsers/" + username;
    }
}

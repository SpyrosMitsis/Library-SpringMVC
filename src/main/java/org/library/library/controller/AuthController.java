package org.library.library.controller;

import org.library.library.dto.RegistrationDto;
import org.library.library.model.AppUser;
import org.library.library.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user= new RegistrationDto();
        model.addAttribute("user", user);
        return "library/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("user") RegistrationDto user,
                           BindingResult bindingResult, Model model) {

        AppUser existingUser = appUserService.findByUsername(user.getUsername());
        if (existingUser != null) {
            bindingResult.rejectValue("username", "error.user", "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "library/register";
        }
        appUserService.saveUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String login() {
        return "library/login";
    }



}

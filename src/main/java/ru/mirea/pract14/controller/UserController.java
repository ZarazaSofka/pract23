package ru.mirea.pract14.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mirea.pract14.entity.User;
import ru.mirea.pract14.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    String signUpGet(@ModelAttribute("user") User user) {
        return "signup";
    }

    @PostMapping("/signup")
    String signUpPost(@ModelAttribute("user") User user) {
        userService.signUpUser(user);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }
}
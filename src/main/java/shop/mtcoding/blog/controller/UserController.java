package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/join-form")
    public String joinForm() {
        return "join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/user/update-form")
    public String updateForm() {
        return "update-form";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}

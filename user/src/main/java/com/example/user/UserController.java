package com.example.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
        try {
            String pass = userService.login(username).getPassword();
            if (pass.equals(password)) {
                return "login succesful";
            } else {
                String errorMessage = "Invalid username or password. Please try again.";
                model.addAttribute("error", errorMessage);
                return "login"; // Return to the login page with the error message
            }
        } catch (NullPointerException e) {
            System.err.println("An error occurred: " + e.getMessage());
            String errorMessage = "Invalid username or password. Please try again.";
            model.addAttribute("error", errorMessage);
            return "login"; // Return to the login page with the error message
        }
    }



}

package com.example.UserManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usermanagement")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserRepository userRepository;

    @PostMapping(path = "/add")
    public User add(@RequestParam("username") String name, @RequestParam("password") String password, @RequestParam("passwordagain") String passwordagain, Model model){
        User user = new User();
        user.setUsername(name);
        if(password.equals(passwordagain)){
            user.setPassword(password);
            userRepository.save(user);
        }
        else{
            String errorMessage = "Invalid username or password. Please try again.";
            model.addAttribute("error", "Passwords are not the same");
        }
        return user;
    }
    @PostMapping(path = "/delete")
    public String deleteUser(@RequestParam("deleteusername") String name, Model model){

        userRepository.delete(name);

        return "You have successfully deleted the user";
    }
}

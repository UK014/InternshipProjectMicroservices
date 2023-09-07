package com.example.UserManagement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagement {
    @GetMapping(path = "/")
    public String showUserManagement(){


        return "UserManagement";
    }
}

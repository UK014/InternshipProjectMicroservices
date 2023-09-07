package com.example.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserLogin {
    @RequestMapping("/")
    public String DisplayLogin(Model model){
        return "login";
    }
}

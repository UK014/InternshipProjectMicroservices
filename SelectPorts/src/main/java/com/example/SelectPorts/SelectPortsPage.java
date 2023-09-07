package com.example.SelectPorts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SelectPortsPage {
    @RequestMapping("/")
    public String DisplayPorts(Model model){
        return "SelectPorts";
    }
}

package com.fastcampus.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
    @GetMapping("/mypages/adminMypage")
    public String adminMypage(){

        return "/mypages/adminMypage";
    }

    @GetMapping("/mypages/userMypage")
    public String userMypage(){

        return "/mypages/userMypage";
    }

}

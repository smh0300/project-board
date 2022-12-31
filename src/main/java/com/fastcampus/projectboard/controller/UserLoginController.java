package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/login")
@Controller
public class UserLoginController {

    private final UserLoginService userLoginService;

    @GetMapping
    public String Login(

    ) {

        return "login";
    }


  }



package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.dto.request.UserSignUpRequest;
import com.fastcampus.projectboard.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/sign-up")
@Controller
public class UserSignUpController {

    private final UserSignUpService userSignUpService;

    @GetMapping
    public String signUp() { return "sign-up"; }

    @PostMapping
    public String createNewUser( UserSignUpRequest dto) {

            userSignUpService.createUser(dto);
            return "redirect:/articles";

    }
}

package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.dto.request.UserSignUpRequest;
import com.fastcampus.projectboard.service.UserSignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/sign-up")
@Controller
public class UserSignUpController {

    private final UserSignUpService userSignUpService;

    @GetMapping
    public String signUp(ModelMap map) {
        return "sign-up";
    }

    @PostMapping
    public String createNewUser(
            @Valid UserSignUpRequest dto,
            BindingResult bindingResult,
            ModelMap map,
            RedirectAttributes rdmap
    ) {
        if(bindingResult.hasErrors()){

            Map<String, String> validatorResult = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                validatorResult.put(error.getField(), error.getDefaultMessage());
            }

            for (String key : validatorResult.keySet()) {
                map.addAttribute(key, validatorResult.get(key));
            }
            System.out.println(bindingResult);
            return "sign-up";
        }
            userSignUpService.createUser(dto);
            rdmap.addFlashAttribute("success","회원가입에 성공했습니다.");
            return "redirect:/login";
    }
}

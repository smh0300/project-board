package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.config.SecurityConfig;
import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import com.fastcampus.projectboard.service.MypageService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class MypageController {

    private final SecurityConfig securityConfig;
    private final MypageService mypageService;

    @GetMapping("/mypages/userMypage")
    public String userMypage(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ModelMap map){
        map.addAttribute("CurrentUser",boardPrincipal.toDto());
        return "mypages/userMypage";
    }

    @PostMapping("/mypages/userMypage/change")
    public String changeUser(
            @RequestParam(required = false ,name="change_nickname") String changeNickname,
            @RequestParam(required = false ,name="change_email") String changeEmail,
            @RequestParam(required = false ,name="change_memo") String changeMemo,
            @RequestParam(required = false ,name="change_password") String changePassword,
            @RequestParam(required = false ,name="change_password_verify") String changePasswordVerify,
            @RequestParam(name="current_password") String currentPassword,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
    ) throws Exception {
        PasswordEncoder encdoer = securityConfig.passwordEncoder();
        Integer passwordChange = 0;
        String encrypted = encdoer.encode(changePassword);
        if(changePassword.equals(changePasswordVerify) && !changePassword.equals("")) {
            passwordChange = 1;
        }

        if(encdoer.matches(currentPassword, boardPrincipal.getPassword())){
            if(passwordChange == 1) {
                mypageService.changeuserinfo(changeNickname, changeEmail, changeMemo, encrypted, boardPrincipal);
            }else{
                mypageService.changeuserinfo(changeNickname, changeEmail, changeMemo, changePassword, boardPrincipal);
            }
        }

        System.out.println("helloworld");

        return "redirect:/articles";

    }

}

package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.config.SecurityConfig;
import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import com.fastcampus.projectboard.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

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
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ModelMap map
    ) throws Exception {

        //여기서 Validate
        Map<String, String> validatorResult = new HashMap<>();
        String emailCheck = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$";
        String passwordCheck = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Integer invalidCount = 0;

        PasswordEncoder encdoer = securityConfig.passwordEncoder();
        String encrypted = encdoer.encode(changePassword);
        Integer passwordChange = 0;


        if (changeNickname.equals("")){
            changeNickname = boardPrincipal.nickname();
        }else{
            if (changeNickname.length()<5) {
                validatorResult.put("validNickname", "닉네임 형식에 맞지 않습니다.(최소 5글자 이상)");
                invalidCount++;
            }
        }
        if (changeEmail.equals("")){
            changeEmail = boardPrincipal.email();
        }else{
            if (!changeEmail.matches(emailCheck)){
                validatorResult.put("validEmail", "이메일 형식에 맞지 않습니다 (ex : hello@world.com)");
                invalidCount++;
            }
        }
        if (changeMemo.equals("")){
            changeMemo = boardPrincipal.memo();
        }
        if (changePassword.equals("")){
            changePassword = boardPrincipal.password();
        }else{
            encrypted = encdoer.encode(changePassword);
            passwordChange = 1;

            if (!changePassword.matches(passwordCheck)){
                validatorResult.put("validPassword", "비밀번호 형식에 맞지 않습니다 (최소 8자 이상, 영문자, 숫자 포함)");
                invalidCount++;
            }
        }

        if (changePasswordVerify.equals("")){
            changePasswordVerify = boardPrincipal.password();
        }
        if (!encdoer.matches(currentPassword,boardPrincipal.getPassword())){
            validatorResult.put("validCurrentPassword", "현재 비밀번호가 아닙니다.");
            invalidCount++;
        }
        if (!changePassword.equals(changePasswordVerify)){
            validatorResult.put("validBothPassword", "변경할 비밀번호와 변경할 비밀번호 확인이 맞지 않습니다");
            invalidCount++;
        }

        if (invalidCount != 0){
            for (String key : validatorResult.keySet()) {
                map.addAttribute(key, validatorResult.get(key));
            }

            map.addAttribute("CurrentUser",boardPrincipal.toDto());
            return "mypages/userMypage";
        }

        if(passwordChange == 1) {
            boardPrincipal = mypageService.changeuserinfo(changeNickname, changeEmail, changeMemo, encrypted, boardPrincipal);
        }else{
            boardPrincipal = mypageService.changeuserinfo(changeNickname, changeEmail, changeMemo, changePassword, boardPrincipal);
        }

        map.addAttribute("CurrentUser",boardPrincipal.toDto());
        map.addAttribute("success","정보변경에 성공했습니다.");
        return "mypages/userMypage";

    }

}

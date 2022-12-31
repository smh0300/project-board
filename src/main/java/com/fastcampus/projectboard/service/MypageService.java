package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final UserAccountRepository userAccountRepository;

    @Transactional
    public BoardPrincipal changeuserinfo(String changeNickname,
                                              String changeEmail,
                                              String changeMemo,
                                              String changePassword,
                                              @AuthenticationPrincipal BoardPrincipal boardprincipal
    ) throws Exception {
//        if (changeNickname.equals("")){
//            changeNickname = boardprincipal.nickname();
//        }
//        if (changeEmail.equals("")){
//            changeEmail = boardprincipal.email();
//        }
//        if (changeMemo.equals("")){
//            changeMemo = boardprincipal.memo();
//        }
//        if (changePassword.equals("")){
//            changePassword = boardprincipal.password();
//        }
//
//        //여기서 Validate
//        Map<String, String> validatorResult = new HashMap<>();
//        String emailCheck = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$";
//        String passwordCheck = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
//        Integer invalidCount = 0;
//
//        if (changeNickname.length()<5) {
//            validatorResult.put("validNickname", "닉네임 형식에 맞지 않습니다.(최소 3글자 이상)");
//            invalidCount++;
//        }
//        if (!changeEmail.matches(emailCheck)){
//            validatorResult.put("validEmail", "이메일 형식에 맞지 않습니다 (ex : hello@world.com)");
//            invalidCount++;
//        }
//        if (!changePassword.matches(passwordCheck)){
//            validatorResult.put("validPassword", "비밀번호 형식에 맞지 않습니다 (최소 8자 이상, 영문자, 숫자 포함)");
//            invalidCount++;
//        }
//
//        if (invalidCount != 0){
//            return validatorResult;
//        }


        //DB수정
        UserAccount userAccount = userAccountRepository.findById(boardprincipal.getUsername()).orElseThrow();
        userAccount.setUserPassword(changePassword);
        userAccount.setNickname(changeNickname);
        userAccount.setMemo(changeMemo);
        userAccount.setEmail(changeEmail);

        //세션수정(DB수정해도 세션은 안바뀌니까 직접 바꿔줘야함)
        UserAccountDto userAccountDto = UserAccountDto.of(boardprincipal.username(), changePassword,changeEmail,changeNickname,changeMemo);

        Authentication authentication = new UsernamePasswordAuthenticationToken(BoardPrincipal.from(userAccountDto),null,boardprincipal.authorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return  BoardPrincipal.from(userAccountDto);
    }

}

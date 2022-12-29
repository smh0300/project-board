package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.config.SecurityConfig;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final UserAccountRepository userAccountRepository;
    private final UserLoginService userLoginService;
    @Transactional
    public void changeuserinfo(String changeNickname,
                               String changeEmail,
                               String changeMemo,
                               String changePassword,
                               @AuthenticationPrincipal BoardPrincipal boardprincipal
    ) throws Exception {
        if (changeNickname.equals("")){
            changeNickname = boardprincipal.nickname();
        }
        if (changeEmail.equals("")){
            changeEmail = boardprincipal.email();
        }
        if (changeMemo.equals("")){
            changeMemo = boardprincipal.memo();
        }
        if (changePassword.equals("")){
            changePassword = boardprincipal.password();
        }

        //DB수정
        UserAccount userAccount = userAccountRepository.findById(boardprincipal.getUsername()).orElseThrow();
        userAccount.setUserPassword(changePassword);
        userAccount.setNickname(changeNickname);
        userAccount.setMemo(changeMemo);
        userAccount.setEmail(changeEmail);

        //세션수정(DB수정해도 세션은 안바뀜)
        UserAccountDto userAccountDto = UserAccountDto.of(boardprincipal.username(), changePassword,changeEmail,changeNickname,changeMemo);


        Authentication authentication = new UsernamePasswordAuthenticationToken(BoardPrincipal.from(userAccountDto),null,boardprincipal.authorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(SecurityContextHolder.getContext().toString());

    }

}

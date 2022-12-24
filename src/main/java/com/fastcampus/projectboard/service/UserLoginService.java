package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserLoginService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public BoardPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = this.userAccountRepository.findByUserId(username);

        if (userAccount.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        UserAccount userAccount1 = userAccount.get();
        String roleTypes = "";

        if (userAccount1.getAdmincheck() == 1) {
            roleTypes = BoardPrincipal.RoleType.ADMIN.getName();
        } else {
            roleTypes = BoardPrincipal.RoleType.USER.getName();
        }

        String[] splitStr = roleTypes.split(",");
        for(int i=0; i<splitStr.length; i++){
            authorities.add(new SimpleGrantedAuthority(splitStr[i]));
        }

        return new BoardPrincipal(userAccount1.getUserId(), userAccount1.getUserPassword(), authorities, userAccount1.getEmail(), userAccount1.getNickname(), userAccount1.getMemo());

    }
}

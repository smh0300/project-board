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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if (userAccount1.getAdmincheck() == 1) {
            authorities.add(new SimpleGrantedAuthority(BoardPrincipal.RoleType.ADMIN.getName()));
        } else {
            authorities.add(new SimpleGrantedAuthority(BoardPrincipal.RoleType.USER.getName()));
        }

        return new BoardPrincipal(userAccount1.getUserId(), userAccount1.getUserPassword(), authorities, userAccount1.getEmail(), userAccount1.getNickname(), userAccount1.getMemo());

    }
}

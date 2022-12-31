package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.dto.request.UserSignUpRequest;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserSignUpService {
    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(UserSignUpRequest dto) {

        String encrypted_password = passwordEncoder.encode(dto.userPassword());
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
                .userId(dto.userId())
                .userPassword(encrypted_password)
                .email(dto.email())
                .nickname(dto.nickname())
                .memo(dto.memo())
                .createdBy(dto.userId())
                .build();

        userAccountRepository.save(userSignUpRequest.toEntity());

        return null;
    }

    @Transactional(readOnly = true)
    public UserAccountDto saveUser(String username, String password, String email, String nickname, String memo) {

        String encrypted_password = passwordEncoder.encode(password);

        return UserAccountDto.from(
                userAccountRepository.save(UserAccount.of(username, encrypted_password, email, nickname, memo, username))
        );
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> searchUser(String username) {
        return userAccountRepository.findById(username)
                .map(UserAccountDto::from);
    }

}

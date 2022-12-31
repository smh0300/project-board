package com.fastcampus.projectboard.dto.request;

import com.fastcampus.projectboard.domain.UserAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
public record UserSignUpRequest(
        @NotBlank(message = "아이디가 비어있습니다.")
        String userId,

        @Builder
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 최소 8자이상(영문자, 숫자 포함)입니다.")
        String userPassword,

        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일형식이 맞지않습니다.(ex : hello@world.com)")
        String email,

        @NotBlank(message = "별명이 비어있습니다.")
        String nickname,

        String memo,

        String createdBy

) {
    public static UserSignUpRequest of(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
        return new UserSignUpRequest(userId, userPassword, email, nickname, memo, createdBy);
    }




    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo,
                createdBy
        );
    }


}

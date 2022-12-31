package com.fastcampus.projectboard.dto.request;

import com.fastcampus.projectboard.domain.UserAccount;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;


@Builder
public record UserSignUpRequest(
        @Length(min = 5, message = "ID 형식에 맞지않습니다 (최소 5자 이상)")
        String userId,

        @Builder
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호 형식에 맞지 않습니다 (최소 8자 이상, 영문자, 숫자 포함)")
        String userPassword,

        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞지 않습니다 (ex : hello@world.com)")
        String email,

        @Length(min = 5, message = "닉네임 형식에 맞지 않습니다.(최소 5자 이상)")
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

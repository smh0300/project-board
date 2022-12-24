package com.fastcampus.projectboard.dto.request;

import com.fastcampus.projectboard.domain.UserAccount;
import lombok.Builder;
import org.springframework.security.core.userdetails.User;


@Builder
public record UserSignUpRequest(
        //@NotBlank
        String userId,

        @Builder
        //@NotBlank
       // @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,}")
        String userPassword,

        //@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
        //@NotBlank
        String email,

        //@NotBlank
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

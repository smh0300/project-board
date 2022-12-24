package com.fastcampus.projectboard.dto.request;

import lombok.Builder;

public record UserLoginRequest(

        String userId,

        @Builder
        String userPassword
) {
        public static UserLoginRequest of(String userId, String userPassword) {
                return new UserLoginRequest(userId, userPassword);
        }

}
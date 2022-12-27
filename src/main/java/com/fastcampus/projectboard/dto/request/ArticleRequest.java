package com.fastcampus.projectboard.dto.request;


import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.UserAccountDto;
import org.springframework.web.multipart.MultipartFile;

public record ArticleRequest(
        String title,
        String content,
        String hashtag,
        MultipartFile multipartFile
) {

    public static ArticleRequest of(String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag, null);
    }

    public static ArticleRequest of(String title, String content, String hashtag, MultipartFile multipartFile) {
        return new ArticleRequest(title, content, hashtag, multipartFile);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtag
        );
    }

}
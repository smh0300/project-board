package com.fastcampus.projectboard.dto;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.File;
import com.fastcampus.projectboard.domain.UserAccount;

import java.time.LocalDateTime;

public record FileDto (

        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String origFilename,
        String uuid,
        String contentType,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
){
    public static FileDto of(Long articleId, UserAccountDto userAccountDto, String origFilename, String uuid, String contentType) {
        return new FileDto(null, articleId, userAccountDto, origFilename, uuid, contentType, null, null, null, null);
    }

    public static FileDto of(Long id, Long articleId, UserAccountDto userAccountDto, String origFilename, String uuid, String contentType, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new FileDto(id, articleId, userAccountDto, origFilename, uuid, contentType, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public File toEntity(Article article, UserAccount userAccount){
        return File.of(article, userAccount, origFilename, uuid, contentType);
    }


}

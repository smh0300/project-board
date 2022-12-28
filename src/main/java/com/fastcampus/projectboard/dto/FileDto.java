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
        Long fileSize,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
){
    public static FileDto of(Long articleId, UserAccountDto userAccountDto, String origFilename, String uuid, String contentType) {
        return new FileDto(null, articleId, userAccountDto, origFilename, uuid, contentType, null, null, null, null, null);
    }

    public static FileDto of(Long id, Long articleId, UserAccountDto userAccountDto, String origFilename, String uuid, String contentType, Long fileSize, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new FileDto(id, articleId, userAccountDto, origFilename, uuid, contentType, fileSize,createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public File toEntity(Article article, UserAccount userAccount){
        return File.of(article, userAccount, origFilename, uuid, contentType, fileSize);
    }

    public static FileDto from(File entity){
        return new FileDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getOrigFilename(),
                entity.getUuid(),
                entity.getContentType(),
                entity.getFileSize(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}

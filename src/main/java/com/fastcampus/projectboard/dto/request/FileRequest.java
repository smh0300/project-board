package com.fastcampus.projectboard.dto.request;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.File;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.FileDto;
import com.fastcampus.projectboard.dto.UserAccountDto;
import lombok.Builder;

@Builder
public record FileRequest (

        Long articleId,
        String origFilename,
        String uuid,
        String contentType
){

    public FileRequest of(Long articleId, String origFilename, String uuid, String contentType) {

        return new FileRequest(articleId, origFilename, uuid, contentType);
    }

    public FileDto toDto(UserAccountDto userAccountDto) {
        return FileDto.of(
                articleId,
                userAccountDto,
                origFilename,
                uuid,
                contentType
        );
    }



}

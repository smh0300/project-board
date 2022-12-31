package com.fastcampus.projectboard.dto.response;

import com.fastcampus.projectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.projectboard.dto.FileDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId,
        Set<ArticleCommentResponse> articleCommentsResponse,
        Set<FileDto> fileDtos
) {

    public static ArticleWithCommentsResponse of(Long id,
                                                 String title,
                                                 String content,
                                                 String hashtag,
                                                 LocalDateTime createdAt,
                                                 String email,
                                                 String nickname,
                                                 String userId,
                                                 Set<ArticleCommentResponse> articleCommentResponses,
                                                 Set<FileDto> fileDtos
    ) {
        return new ArticleWithCommentsResponse(id,
                title,
                content,
                hashtag,
                createdAt,
                email,
                nickname,
                userId,
                articleCommentResponses,
                fileDtos);
    }



    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleWithCommentsResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId(),
                dto.articleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                new LinkedHashSet<>(dto.fileDtos())
        );
    }

}
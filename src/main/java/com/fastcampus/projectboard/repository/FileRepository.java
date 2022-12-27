package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.File;
import com.fastcampus.projectboard.domain.QFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface FileRepository extends
        JpaRepository<File, Long>{

    List<File> findByArticle_Id(Long articleId);

    void deleteByIdAndUserAccount_UserId(Long fileID, String userId);
}
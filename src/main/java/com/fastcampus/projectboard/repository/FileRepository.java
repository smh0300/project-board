package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface FileRepository extends
        JpaRepository<File, Long>{

    Optional<File> findByArticle_IdAndUuid(Long Article_Id, String Uuid);

    void deleteByIdAndUserAccount_UserId(Long fileID, String userId);
}
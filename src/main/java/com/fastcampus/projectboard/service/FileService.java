package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import com.fastcampus.projectboard.repository.FileRepository;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    @Value("${custom.path.upload-files}")
    private String fileDir;

    public void saveFile(UserAccountDto dto, List<MultipartFile> multipartFile, Long article_count) throws IOException {
        Article article = articleRepository.findById(article_count)
                .orElseThrow(IllegalArgumentException::new);

        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userId());

        for(MultipartFile file : multipartFile) {

            String origFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String ext = origFilename.substring(origFilename.lastIndexOf("."));
            String savedName = uuid + ext;
            String contentType = file.getContentType();
            Long fileSize = file.getSize();

            file.transferTo(new File(fileDir + savedName));

            com.fastcampus.projectboard.domain.File file1 = com.fastcampus.projectboard.domain.File.of(article,userAccount,origFilename,uuid,contentType,fileSize,ext);

            fileRepository.save(file1);

        }
    }

    public ResponseEntity<Resource> downloadFile(Long articleId, String uuid){
        try {
            com.fastcampus.projectboard.domain.File file = fileRepository.findByArticle_IdAndUuid(articleId, uuid)
                    .orElseThrow(IllegalArgumentException::new);

            //윈도 버전
            Path filePath = Paths.get(fileDir  + file.getUuid() + file.getExt());

            InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toString()));
            String fileName = file.getOrigFilename();

            String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);


            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .cacheControl(CacheControl.noCache())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName +"\"")
                    .body(resource);

        }catch (Exception e){
            log.warn(e.getLocalizedMessage());
            return null;
        }




    }



}

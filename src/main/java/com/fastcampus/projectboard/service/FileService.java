package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import com.fastcampus.projectboard.repository.FileRepository;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


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

            file.transferTo(new File(fileDir + "\\" + savedName));

            com.fastcampus.projectboard.domain.File file1 = com.fastcampus.projectboard.domain.File.of(article,userAccount,origFilename,uuid,contentType);

            fileRepository.save(file1);

        }
    }



}

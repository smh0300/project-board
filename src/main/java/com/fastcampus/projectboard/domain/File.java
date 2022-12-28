package com.fastcampus.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "origFilename"),
        @Index(columnList = "uuid"),
        @Index(columnList = "contentType")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class File extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article; // 게시글 (ID)
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount; // 유저 정보 (ID)
    @Setter @Column(nullable = false, length = 500) private String origFilename; // 실제파일이름
    @Setter @Column(nullable = false, length = 500) private String uuid; // 저장된파일이름
    @Setter @Column(nullable = false, length = 500) private String contentType; // 파일형식
    @Setter @Column(nullable = false) private Long fileSize;
    @Setter @Column(nullable = false) private String ext;
    protected File() {}

    private File(Article article, UserAccount userAccount, String origFilename, String uuid, String contentType, Long fileSize, String ext) {
        this.article = article;
        this.userAccount = userAccount;
        this.origFilename = origFilename;
        this.uuid = uuid;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.ext = ext;
    }

    public static File of(Article article, UserAccount userAccount, String origFilename, String uuid, String contentType, Long fileSize, String ext) {
        return new File(article, userAccount, origFilename, uuid, contentType, fileSize, ext);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
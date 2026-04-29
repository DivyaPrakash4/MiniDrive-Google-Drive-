package com.miniDrive.MiniDrive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String s3Key;

    private Long fileSize;

    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

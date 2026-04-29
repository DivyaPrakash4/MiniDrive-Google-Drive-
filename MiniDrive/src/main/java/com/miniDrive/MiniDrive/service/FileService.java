package com.miniDrive.MiniDrive.service;

import com.miniDrive.MiniDrive.entity.FileEntity;
import com.miniDrive.MiniDrive.entity.User;
import com.miniDrive.MiniDrive.repository.FileRepository;
import com.miniDrive.MiniDrive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final S3Client s3Client;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public FileService(S3Client s3Client,
                       FileRepository fileRepository,
                       UserRepository userRepository) {
        this.s3Client = s3Client;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    /* =========================
       UPLOAD FILE
       ========================= */
    public void uploadFile(MultipartFile file, String userEmail) throws IOException {

        // File size validation
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size exceeds 5MB limit");
        }

        // File type validation
        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("application/pdf")
                        || contentType.equals("image/png")
                        || contentType.equals("image/jpeg"))) {

            throw new RuntimeException("Only PDF, JPG, PNG files are allowed");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String s3Key = UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .contentType(contentType)
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setS3Key(s3Key);
        fileEntity.setFileSize(file.getSize());
        fileEntity.setUploadTime(LocalDateTime.now());
        fileEntity.setUser(user);

        fileRepository.save(fileEntity);
    }

    /* =========================
       LIST FILES
       ========================= */
    public List<FileEntity> getFiles(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return fileRepository.findByUser(user);
    }

    /* =========================
       DOWNLOAD FILE
       ========================= */
    public String generateDownloadUrl(Long fileId) {

        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        try (S3Presigner presigner = S3Presigner.builder()
                .region(s3Client.serviceClientConfiguration().region())
                .credentialsProvider(
                        s3Client.serviceClientConfiguration().credentialsProvider()
                )
                .build()) {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getS3Key())
                    .build();

            GetObjectPresignRequest presignRequest =
                    GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(10))
                            .getObjectRequest(getObjectRequest)
                            .build();

            PresignedGetObjectRequest presignedRequest =
                    presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toString();
        }
    }

    /* =========================
       DELETE FILE
       ========================= */
    public void deleteFile(Long fileId) {

        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getS3Key())
                .build();

        s3Client.deleteObject(deleteRequest);
        fileRepository.delete(file);
    }
}

package com.miniDrive.MiniDrive.controller;

import com.miniDrive.MiniDrive.entity.FileEntity;
import com.miniDrive.MiniDrive.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // ✅ LIST FILES
    @GetMapping
    public List<FileEntity> listFiles(Authentication auth) {
        return fileService.getFiles(auth.getName());
    }

    // ✅ UPLOAD FILE
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file,
            Authentication auth
    ) throws Exception {

        fileService.uploadFile(file, auth.getName());
        return ResponseEntity.ok().build();
    }

    // ✅ DOWNLOAD FILE
    @GetMapping("/download/{id}")
    public String download(@PathVariable Long id) {
        return fileService.generateDownloadUrl(id);
    }

    // ✅ DELETE FILE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok().build();
    }
}

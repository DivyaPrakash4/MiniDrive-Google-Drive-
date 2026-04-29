package com.miniDrive.MiniDrive.repository;

import com.miniDrive.MiniDrive.entity.FileEntity;
import com.miniDrive.MiniDrive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByUser(User user);
}

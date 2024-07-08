package com.web.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(MultipartFile file, String directory);
    void deleteFile(String fileName, String directory);
}
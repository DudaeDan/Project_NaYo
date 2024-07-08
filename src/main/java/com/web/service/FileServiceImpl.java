package com.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String saveFile(MultipartFile file, String directory) {
        try {
            if (file.isEmpty()) {
                return null;
            }

            // 디렉토리 생성
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일 저장
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(directory, fileName);
            Files.write(filePath, file.getBytes());

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteFile(String fileName, String directory) {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        File file = new File(directory, fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
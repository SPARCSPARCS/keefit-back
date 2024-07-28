package com.backend.api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileContentReader {

    public String readFileContentAsText(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IOException("파일이 존재하지 않습니다: " + filePath);
        }

        System.out.println("파일 경로 확인: " + filePath.toAbsolutePath());

        // 파일의 내용을 바이트 배열로 읽어와 문자열로 변환하기
        byte[] fileBytes = Files.readAllBytes(filePath);
        String fileContent = new String(fileBytes);
        System.out.println("파일 내용 확인: " + fileContent);

        return fileContent;
    }
}
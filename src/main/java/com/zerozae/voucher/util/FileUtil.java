package com.zerozae.voucher.util;

import com.zerozae.voucher.exception.ErrorMessage;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {

    private static final String TEMP = ".temp";

    public void createFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e){
                throw ErrorMessage.error("파일 생성 중 문제가 발생했습니다.");
            }
        }
    }

    public void saveToCsvFile(String saveData, String filePath){
        try {
            Files.writeString(Path.of(filePath), saveData, StandardOpenOption.APPEND);
        }catch (Exception e){
            throw ErrorMessage.error("파일 저장 중 문제가 발생했습니다.");
        }
    }

    public List<String> loadFromCsvFile(String filePath){
        try {
            return Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);
        }catch (Exception e){
            throw ErrorMessage.error("파일로부터 데이터를 읽어오던 중 문제가 발생했습니다.");
        }
    }

    public void updateFile(String targetInfo, UUID targetId, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + TEMP))) {

            String dataInfo;
            while ((dataInfo = br.readLine()) != null) {
                String fileInfo = dataInfo.split(",")[0];
                if (targetId.toString().equals(fileInfo)) {
                    bw.write(targetInfo);
                    bw.newLine();
                } else {
                    bw.write(dataInfo);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw ErrorMessage.error("파일 수정 중 오류가 발생했습니다.");
        }
        backupAndRenameFile(filePath);
    }

    public void clearDataFile(String filePath) {
        File file = new File(filePath);

        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw ErrorMessage.error("파일의 데이터를 모두 삭제하던 중 오류가 발생했습니다.");
        }
    }

    public void deleteFileDataById(UUID targetId, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + TEMP))) {

            String line;
            while ((line = br.readLine()) != null) {
                String readData = line.split(",")[0];
                UUID lineId = UUID.fromString(readData);

                if (!lineId.equals(targetId)) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw ErrorMessage.error("파일 데이터 삭제 작업 중 오류가 발생했습니다.");
        }
        backupAndRenameFile(filePath);
    }

    private void backupAndRenameFile(String filePath) {
        File tempFile = new File(filePath + TEMP);
        File targetFile = new File(filePath);
        tempFile.renameTo(targetFile);
    }
}

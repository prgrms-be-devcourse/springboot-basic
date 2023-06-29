package com.programmers.voucher.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CsvBlackListStream implements BlackListStream {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    @Value("${filepath.blacklist}")
    private String SAMPLE_CSV_FILE_PATH;
    BufferedReader br;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public List<String> findAll() throws IOException {
        String line = "";
        String path = resourceLoader.getResource(SAMPLE_CSV_FILE_PATH).getURI().getPath();
        File file = new File(path);
        List<String> blackList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            loadCSVFile(blackList, br);
        } catch (Exception e) {
            log.warn("파일을 읽어들이는 도중 error 발생 | [error] : {}", e.getMessage());
            throw new IllegalStateException("파일을 읽어들이는 도중 에러가 발생했습니다. [Error Message] : " + e.getMessage());
        }
        return blackList;
    }

    private void loadCSVFile(List<String> blackList, BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            List<String> rowInformation = Arrays.asList(line.split(","));
            blackList.add(blackList.size(), rowInformation.get(0));
        }
    }

    private void closeBufferedReader() {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            log.warn("BufferedReader 종료 중 에러 발생 | [error] : {}", e.getMessage());
            e.printStackTrace();
        }
    }
}

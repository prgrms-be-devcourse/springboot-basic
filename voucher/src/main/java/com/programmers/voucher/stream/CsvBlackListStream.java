package com.programmers.voucher.stream;

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
    private static final String SAMPLE_CSV_FILE_PATH = "classpath:/customer_blacklist.csv";
    BufferedReader br;

    @Override
    public List<String> findAll() throws IOException {
        String line = "";
        String path = resourceLoader.getResource(SAMPLE_CSV_FILE_PATH).getURI().getPath();
        File file = new File(path);
        List<String> blackList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            loadCSVFile(blackList, br);
        } catch (Exception e) {
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
            e.printStackTrace();
        }
    }
}

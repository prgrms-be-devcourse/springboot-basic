package com.programmers.voucher.stream;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository
public class CsvBlackListStream implements BlackListStream {
    private static final String SAMPLE_CSV_FILE_PATH = "/Users/tommy/Desktop/dev course/과제/SpringBootBasic/voucher/src/main/resources/customer_blacklist.csv";
    File file = new File(SAMPLE_CSV_FILE_PATH);
    BufferedReader br;

    @Override
    public List<String> findAll() {
        String line = "";
        List<String> blackList = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                List<String> rowInformation = Arrays.asList(line.split(","));
                blackList.add(blackList.size(), rowInformation.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeBufferedReader();
        }
        return blackList;
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

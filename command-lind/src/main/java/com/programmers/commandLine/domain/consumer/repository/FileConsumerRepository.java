package com.programmers.commandLine.domain.consumer.repository;

import com.programmers.commandLine.global.factory.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class FileConsumerRepository {
    private final String filePath = "./src/main/resources/consumer_blacklist.csv";
    private final File file = new File(filePath);

    public Map<String, String> findAll() {
        LoggerFactory.getLogger().info("FileConsumerRepository findAll 실행");

        Map<String, String> consumerMap = new LinkedHashMap<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] findLine = line.split(",");

                consumerMap.put(findLine[0], findLine[1]);
            }

            return consumerMap;

        } catch (IOException e) {
            LoggerFactory.getLogger().error("FileConsumerRepository findAll 에러발생");
            throw new IllegalArgumentException("FileConsumerRepository findAll 에러발생");
        }

    }
}

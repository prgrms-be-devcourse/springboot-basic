package com.devcourse.user.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.devcourse.global.common.Constant.DELIMITER;
import static com.devcourse.global.common.Constant.FILE_READ_FAIL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Repository
public class BlackListRepository {
    private static final String DEFAULT_DELIMITER = ", ";

    private final File blackList;

    public BlackListRepository(@Value("${path.csv}") String path) {
        blackList = new File(path);
    }

    public List<String> findAllBlack() {
        try (BufferedReader reader = new BufferedReader(new FileReader(blackList, UTF_8))) {
            return reader.lines()
                    .map(line -> line.replace(DEFAULT_DELIMITER, DELIMITER))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(FILE_READ_FAIL);
        }
    }
}

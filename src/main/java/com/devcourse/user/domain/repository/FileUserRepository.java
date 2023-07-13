package com.devcourse.user.domain.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.devcourse.global.common.Constant.DELIMITER;
import static com.devcourse.global.common.Constant.FILE_READ_FAIL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Profile({"dev", "file"})
class FileUserRepository implements UserRepository {
    private static final String DEFAULT_DELIMITER = ", ";
    private final File blackList = new File("src/main/resources/file/customer_blackList.csv");

    @Override
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

package com.devcourse.user.domain.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Profile("dev")
class FileUserRepository implements UserRepository {
    private final File blackList = new File("src/main/resources/file/customer_blackList.csv");

    @Override
    public List<String> findAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(blackList, UTF_8))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

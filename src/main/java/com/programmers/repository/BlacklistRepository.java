package com.programmers.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class BlacklistRepository {

    private final Path path;

    public BlacklistRepository(@Value("${file.blacklist.file-path}") String filePath) {
        this.path = Paths.get(filePath);
    }

    public List<String> findAll() {
        List<String> blacklist = new ArrayList<>();

        try {
            if (Files.exists(path)) {
                List<String> fileBlacklist = Files.readAllLines(path).stream()
                        .toList();

                blacklist.addAll(fileBlacklist);
            }
        } catch (IOException ignored) {
        }

        return Collections.unmodifiableList(blacklist);
    }
}

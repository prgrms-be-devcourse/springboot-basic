package com.programmers.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlacklistRepository {

    private final File file;

    public BlacklistRepository(@Value("${file.blacklist.file-path}") String filePath) {
        this.file = new File(filePath);
    }

    public List<String> findAll() {
        List<String> blacklist = new ArrayList<>();

        try {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    blacklist.add(line);
                }

                reader.close();
            }
        } catch (IOException e) {
        }
        return blacklist;
    }
}

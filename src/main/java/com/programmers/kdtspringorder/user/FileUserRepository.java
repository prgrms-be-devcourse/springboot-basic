package com.programmers.kdtspringorder.user;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileUserRepository implements UserRepository {

    private final File blacklist;

    public FileUserRepository() throws IOException {
        blacklist = new File("customer_blacklist.csv");
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUserId(String userId) {

        try {
            BufferedReader bufferedReader = Files.newBufferedReader(blacklist.toPath(), StandardCharsets.UTF_8);
            return bufferedReader.lines()
                    .map(str -> str.split(","))
                    .filter(arr -> arr[1].equals(userId))
                    .map(arr -> getUser(arr))
                    .findFirst();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        try {
            List<String> strings = Files.readAllLines(blacklist.toPath(), StandardCharsets.UTF_8);
            return strings.stream()
                    .map(str -> str.split(","))
                    .map(arr -> getUser(arr))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private User getUser(String[] arr) {
        return new User(arr[0], arr[1]);
    }
}

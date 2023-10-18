package team.marco.vouchermanagementsystem.repository;

import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.User;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Repository
public class BlacklistRepository {
    private static final String PATH = "src/main/resources/blacklist.csv";
    private List<User> blacklist = new ArrayList<>();

    public BlacklistRepository() {
        blacklist = load();
    }

    private BufferedWriter getWriter() {
        try {
            return Files.newBufferedWriter(Paths.get(PATH), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) { throw new RuntimeException("파일 쓰기 실패"); }
    }

    private BufferedReader getReader() {
        try {
            return Files.newBufferedReader(Paths.get(PATH), StandardCharsets.UTF_8);
        } catch (IOException e) { throw new RuntimeException("파일 읽기 실패"); }
    }

    private List<User> load() {
        List<User> loaded = new ArrayList<>();

        try (BufferedReader reader = getReader()) {
            reader.readLine();
            reader.lines()
                    .map(s -> s.split("[;,]"))
                    .forEach(data -> {
                        loaded.add(new User(UUID.fromString(data[0]), data[1]));
                    });
        } catch (IOException e) { throw new RuntimeException("데이터를 가져올 수 없습니다." + e.getMessage()); }
        return loaded;

    }

    public List<User> findAll() {
        return blacklist;
    }
}

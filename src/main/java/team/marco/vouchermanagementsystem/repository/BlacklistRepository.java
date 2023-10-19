package team.marco.vouchermanagementsystem.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BlacklistRepository {
    private final List<User> blacklist;

    public BlacklistRepository(@Value("${file.path.blacklist}") String path) {
        blacklist = load(path);
    }

    private List<User> load(String path) {
        List<User> loaded = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            reader.readLine(); // skip header
            reader.lines()
                    .map(s -> s.split("[;,]"))
                    .forEach(data -> loaded.add(new User(UUID.fromString(data[0]), data[1])));
        } catch (IOException e) {
            throw new RuntimeException("데이터를 가져올 수 없습니다.");
        }

        return loaded;
    }

    public List<User> findAll() {
        return blacklist;
    }
}

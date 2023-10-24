package team.marco.vouchermanagementsystem.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class BlacklistRepository {
    private static final String CSV_REGEX = "[;,]";

    private final List<User> blacklist;

    public BlacklistRepository(@Value("${file.path.blacklist}") String path) {
        blacklist = load(path);
    }

    private List<User> load(String path) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            reader.readLine(); // skip header

            return reader.lines()
                    .map(s -> s.split(CSV_REGEX))
                    .map(data -> new User(UUID.fromString(data[0]), data[1]))
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<User> findAll() {
        return Collections.unmodifiableList(blacklist);
    }
}

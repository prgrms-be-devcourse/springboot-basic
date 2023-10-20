package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.User;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileUserRepository implements UserRepository {

    private final Map<UUID, User> store = new ConcurrentHashMap<>();
    private final String filePath;
    private static final String CSV_SEPARATOR = ",";

    public FileUserRepository(@Value("${file.path.blacklist}") String filePath) {
        this.filePath = filePath;
        readAndStoreInMemory();
    }

    private void readAndStoreInMemory() {
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            List<String> fileStrings = readFile(inputStream);
            fileStrings.stream().filter(s -> !s.equals(fileStrings.get(0)))
                    .map(str -> str.split(CSV_SEPARATOR)).forEach(split -> {
                        String name = split[0];
                        Boolean isBlack = Boolean.valueOf(split[1]);
                        User user = new User(UUID.randomUUID(), name, isBlack);
                        store.put(user.getUserId(), user);
                    });
        } catch (IOException e) {
            throw new NotFoundException(ExceptionMessage.NOT_FOUND_FILE.getMessage());
        }
    }

    private List<String> readFile(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public List<User> findBlackAll() {
        return store.values().stream().filter(User::checkIsBlack).toList();
    }
}

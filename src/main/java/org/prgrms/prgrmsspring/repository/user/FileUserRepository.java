package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.User;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileUserRepository implements UserRepository {

    private final Map<UUID, User> store = new ConcurrentHashMap<>();
    private final String filePath;

    public FileUserRepository(@Value("${file.path.blacklist}") String filePath) {
        this.filePath = filePath;
        read();
    }

    private void read() {
        Resource resource = new ClassPathResource(filePath);
        try {
            File file = resource.getFile();
            List<String> strings = Files.readAllLines(file.toPath());
            strings.stream().filter(s -> !s.equals(strings.get(0)))
                    .map(name -> new User(UUID.randomUUID(), name))
                    .forEach(user -> store.put(user.getUserId(), user));
        } catch (IOException e) {
            throw new NotFoundException(ExceptionMessage.NOT_FOUND_FILE.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().toList();
    }
}

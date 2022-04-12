package org.prgms.management.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "default"})
public class BlackListFileRepository implements BlackListRepository {
    @Value("${filedb.blacklist}")
    private String filePath;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private File file;

    @Override
    public Map<UUID, String> getAll() {
        try {
            file = new ClassPathResource(filePath).getFile();
            file.createNewFile();
            List<String> lines = Files.readAllLines(Path.of(file.getPath()));
            Map<UUID, String> map = new ConcurrentHashMap<>();

            for (String line : lines) {
                String[] str = line.split(",");
                UUID uuid = UUID.fromString(str[0]);
                String username = str[1];
                map.put(uuid, filePath);
            }
            return map;
        } catch (Throwable e) {
            logger.error(MessageFormat.format
                    ("{0} can't read blacklist file", e.getMessage()));
            return null;
        }
    }
}

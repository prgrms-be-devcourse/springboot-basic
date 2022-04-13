package org.prgms.management.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "default"})
public class BlackListFileRepository implements BlackListRepository {
    @Value("${filedb.blacklist}")
    private String filePath;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<UUID, String> getAll() {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))
        ) {
            Map<UUID, String> map = new ConcurrentHashMap<>();

            bufferedReader.lines().forEach(
                    line -> {
                        String[] str = line.split(",");
                        UUID uuid = UUID.fromString(str[0]);
                        String username = str[1];
                        map.put(uuid, username);
                    }
            );
            return map;
        } catch (Throwable e) {
            logger.error("{} can't read blacklist file", e.getMessage());
            return null;
        }
    }
}

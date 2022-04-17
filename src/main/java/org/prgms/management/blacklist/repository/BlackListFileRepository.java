package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "test"})
public class BlackListFileRepository implements BlackListRepository {
    @Value("${filedb.blacklist}")
    private String filePath;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<UUID, Blacklist> getAll() {
        Map<UUID, Blacklist> map = new ConcurrentHashMap<>();

        try (
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(filePath))
        ) {
            bufferedReader.lines().forEach(
                    line -> {
                        String[] str = line.split(",");
                        UUID blacklistId = UUID.fromString(str[0]);
                        UUID customerId = UUID.fromString(str[1]);
                        map.put(blacklistId, new Blacklist(blacklistId, customerId));
                    }
            );
        } catch (IOException e) {
            logger.error("{} can't read blacklist file", e.getMessage());
        }

        return map;
    }

    @Override
    public Optional<Blacklist> insert(Blacklist blacklist) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter(filePath, true)
                );
        ) {
            String blacklistStr = MessageFormat.format("{0},{1}\r\n",
                    blacklist.getBlacklistId(), blacklist.getCustomerId());

            bufferedWriter.write(blacklistStr);
            return Optional.of(blacklist);
        } catch (IOException e) {
            logger.error("{} can't insert blacklist file", e.getMessage(), e);
        }
        return Optional.empty();
    }
}

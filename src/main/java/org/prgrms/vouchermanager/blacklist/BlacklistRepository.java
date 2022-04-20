package org.prgrms.vouchermanager.blacklist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BlacklistRepository {

    private final Logger log = LoggerFactory.getLogger(BlacklistRepository.class);
    private final Map<UUID, Blacklist> storage = new ConcurrentHashMap<>();
    private final String blacklistPath;

    public BlacklistRepository(@Value("${blacklist.path}") String blacklistPath) {
        this.blacklistPath = blacklistPath;
    }

    @PostConstruct
    void init() {
        try {
            for (String row : Files.readAllLines(Path.of(blacklistPath))) {
                StringTokenizer st = new StringTokenizer(row, ",");
                UUID blockCustomerId = UUID.fromString(st.nextToken());
                Blacklist blockCustomer = new Blacklist(blockCustomerId, st.nextToken());
                if (storage.get(blockCustomerId) != null) throw new IllegalArgumentException();
                storage.put(blockCustomerId, blockCustomer);
            }
        } catch (Exception e) {
            log.error("Blacklist를 읽어오는데 실패하였습니다. blacklist.path = {}", blacklistPath);
            throw new IllegalArgumentException(MessageFormat.format("{0}", e.getMessage()));
        }
    }

    List<Blacklist> getAll() {
        return new ArrayList<>(storage.values());
    }

    Optional<Blacklist> findById(UUID blockCustomerId) {
        return Optional.ofNullable(storage.get(blockCustomerId));
    }
}



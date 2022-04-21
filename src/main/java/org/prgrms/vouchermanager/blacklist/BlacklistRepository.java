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

/**
 * resource 폴더의 customer_blacklist.csv에 저장된 목록을 읽어오는 repository입니다.
 * custmer_blacklist.csv는 'UUID, email' 의 형식으로 저장되어있습니다.
 * 예시:
 * 5153948e-bf99-11ec-9d64-0242ac120002, "blacklist01@email.com"
 */
@Repository
public class BlacklistRepository {

    private final Logger log = LoggerFactory.getLogger(BlacklistRepository.class);
    private final Map<UUID, Blacklist> storage = new ConcurrentHashMap<>();
    private final String blacklistPath;

    public BlacklistRepository(@Value("${blacklist.path}") String blacklistPath) {
        this.blacklistPath = blacklistPath;
    }

    /**
     * init할 때, customer_blacklist.csv를 한 번만 읽어와 Map storage에 저장하여 조회합니다.
     */
    @PostConstruct
    void init() {
        try {
            for (String row : Files.readAllLines(Path.of(blacklistPath))) {
                StringTokenizer st = new StringTokenizer(row, ",");
                UUID blockCustomerId = UUID.fromString(st.nextToken());
                Blacklist blockCustomer = new Blacklist(blockCustomerId, st.nextToken());
                if (storage.get(blockCustomerId) != null) throw new IllegalArgumentException("customer_blacklist.csv에 중복이 존재합니다.");
                storage.put(blockCustomerId, blockCustomer);
            }
        } catch (Exception e) {
            log.error("customer_blacklist.csv를 읽어오는데 실패하였습니다. blacklist.path = {}", blacklistPath);
            throw new IllegalStateException(MessageFormat.format("{0}", e.getMessage()));
        }
    }

    List<Blacklist> getAll() {
        return new ArrayList<>(storage.values());
    }

    Optional<Blacklist> findById(UUID blockCustomerId) {
        return Optional.ofNullable(storage.get(blockCustomerId));
    }
}



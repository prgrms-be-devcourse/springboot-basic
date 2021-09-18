package com.prgrms.w3springboot.customer.repository;

import com.prgrms.w3springboot.customer.BlacklistCustomer;
import com.prgrms.w3springboot.customer.BlacklistProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BlacklistRepository {
    private static final Map<UUID, BlacklistCustomer> storage = new ConcurrentHashMap<>();
    private static final String DELIMITER = ",";
    private static final int INDEX_UUID = 0;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_REASON = 2;

    private final ApplicationContext resourceLoader;
    private final BlacklistProperties blacklistProperties;

    public BlacklistRepository(ApplicationContext resourceLoader, BlacklistProperties blacklistProperties) {
        this.resourceLoader = resourceLoader;
        this.blacklistProperties = blacklistProperties;
    }

    @PostConstruct
    public void postConstruct() {
        Resource resource = resourceLoader.getResource(blacklistProperties.getFilePath() + blacklistProperties.getFileName());
        try {
            List<String> lines = Files.readAllLines(resource.getFile().toPath());
            for (var line : lines) {
                String[] blacklistCustomerInfo = line.split(DELIMITER);
                UUID blacklistCustomerId = UUID.fromString(blacklistCustomerInfo[INDEX_UUID]);
                String customerName = blacklistCustomerInfo[INDEX_NAME];
                String reason = blacklistCustomerInfo[INDEX_REASON];
                storage.put(blacklistCustomerId, new BlacklistCustomer(blacklistCustomerId, customerName, reason));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BlacklistCustomer> findAll() {
        return new ArrayList<>(storage.values());
    }
}

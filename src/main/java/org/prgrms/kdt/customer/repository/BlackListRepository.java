package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.BlackList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BlackListRepository {

    static final Map<UUID, BlackList> blacklistMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(BlackListRepository.class);
    private static final Path FILE_PATH = Paths.get(System.getProperty("user.dir"), "src/main/resources/customer_blacklist.csv");
    private static final String DELIMITER = ",";
    private static final int INDEX_UUID = 0;
    private static final int INDEX_NAME = 1;

    @PostConstruct
    public static void fileRead() {
        try {
            final List<String> lines = Files.readAllLines(FILE_PATH);
            for (final var line : lines) {
                final String[] customerBlacklist = line.split(DELIMITER);
                final UUID customerId = UUID.fromString(customerBlacklist[INDEX_UUID]);
                final String customerName = customerBlacklist[INDEX_NAME];

                blacklistMap.put(customerId, new BlackList(customerId, customerName, true));
            }
            System.out.println("Blacklist File Read Complete!");
        } catch (final IOException e) {
            logger.error("{}", e.getMessage());
        }
    }

    public BlackList insert(final BlackList blackList) {
        blacklistMap.put(blackList.getCustomerId(), blackList);
        return blackList;
    }

    public List<BlackList> findAllBlacklist() {
        return new ArrayList<>(blacklistMap.values());
    }
}

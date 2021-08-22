package org.prgrms.devcourse.repository;

import org.prgrms.devcourse.domain.BlackUser;
import org.prgrms.devcourse.util.BlackUserFileReader;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileBlackUserRepository implements BlackUserRepository {
    private final ConcurrentHashMap<UUID, BlackUser> blackUserMap = new ConcurrentHashMap<>();
    private static final String BLACK_USER_DATA_SOURCE = "data/customer_blacklist.csv";

    @Override
    public Optional<BlackUser> findByName(String name) {
        return blackUserMap.values().stream()
                .filter(blackUser -> blackUser.getName().equals(name))
                .findAny();
    }

    @Override
    public List<BlackUser> findAll() {
        return new ArrayList<>(blackUserMap.values());
    }

    @PostConstruct
    public void postConstruct() {
        BlackUserFileReader blackUserFileReader = new BlackUserFileReader();
        blackUserMap.putAll(blackUserFileReader.readFile(BLACK_USER_DATA_SOURCE));
    }
}

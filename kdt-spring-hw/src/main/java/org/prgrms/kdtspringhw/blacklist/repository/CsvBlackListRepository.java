package org.prgrms.kdtspringhw.blacklist.repository;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.configuration.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class CsvBlackListRepository implements BlackListRepository{


    private Map<UUID,BlackList> storage = new ConcurrentHashMap<>();

    public void setStorage(Map<UUID, BlackList> storage) {
        this.storage = storage;
    }

    @Override
    public Optional<BlackList> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public BlackList insert(BlackList blackList) {
        storage.put(blackList.getBlackListId(),blackList);
        return blackList;
    }

    @Override
    public Map<UUID, BlackList> returnAll() {
        return storage;
    }
}

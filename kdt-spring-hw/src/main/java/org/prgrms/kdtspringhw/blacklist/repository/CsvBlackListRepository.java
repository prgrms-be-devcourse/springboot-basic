package org.prgrms.kdtspringhw.blacklist.repository;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.blacklist.BlackListService;
import org.prgrms.kdtspringhw.utils.BlackListReader;
import org.prgrms.kdtspringhw.utils.BlackListWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CsvBlackListRepository implements BlackListRepository {

    private BlackListReader blackListReader;
    private Map<UUID, BlackList> storage = new ConcurrentHashMap<>();

    public CsvBlackListRepository() throws IOException {
        blackListReader = new BlackListReader("csv/customer_blacklist.csv");
    }

    @Override
    public Optional<BlackList> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public BlackList insert(BlackList blackList) {
        storage.put(blackList.getBlackListId(), blackList);
        return blackList;
    }

    @Override
    public Map<UUID, BlackList> returnAll() {
        return storage;
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        storage = blackListReader.readCsv();
    }
}

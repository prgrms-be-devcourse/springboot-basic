package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.blacklist.Blacklist;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.util.FileConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileBlacklistRepository implements BlacklistRepository, FileConnector<Blacklist> {

    private static final Logger logger = LoggerFactory.getLogger(FileBlacklistRepository.class);

    private Map<UUID, Blacklist> storage = new ConcurrentHashMap<>();

    private final String PATH = "src/main/resources/blacklist.csv";

    @PostConstruct
    private void postConstruct() {
        storage = fileConnect(PATH);
    }

    @PreDestroy
    private void preDestory(){
        fileInsert(PATH, storage);
    }

    @Override
    public Optional<Blacklist> findById(UUID blacklistId) {
        return Optional.ofNullable(storage.get(blacklistId));
    }

    @Override
    public void save(Blacklist blacklist) {
        storage.put(blacklist.getBlacklistId(), blacklist);
    }

    @Override
    public List<Blacklist> findAll() {
        return new ArrayList<>(storage.values());
    }

}

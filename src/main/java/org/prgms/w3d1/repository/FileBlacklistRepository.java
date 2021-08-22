package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.blacklist.Blacklist;
import org.prgms.w3d1.model.voucher.Voucher;
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
public class FileBlacklistRepository implements BlacklistRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileBlacklistRepository.class);

    private Map<UUID, Blacklist> storage = new ConcurrentHashMap<>();

    private final String PATH = "src/main/resources/blacklist.csv";

    @PostConstruct
    private void postConstruct() {
        fileConnect(PATH);
    }

    @PreDestroy
    private void preDestory(){
        fileInsert(PATH);
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

    private void fileConnect(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            storage = (ConcurrentHashMap<UUID, Blacklist>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            storage = new ConcurrentHashMap<>();
            logger.error(MessageFormat.format("Error on FileBlacklistRepositoy : {0}", e.getClass().getCanonicalName()));
        }
    }

    private void fileInsert(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(storage);
            fos.close();
            oos.close();
        } catch (IOException e) {
            logger.error(MessageFormat.format("Error on FileBlacklistRepositoy : {0}", e.getClass().getCanonicalName()));
        }
    }
}

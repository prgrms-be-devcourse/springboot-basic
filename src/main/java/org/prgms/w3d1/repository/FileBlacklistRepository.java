package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.blacklist.Blacklist;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileBlacklistRepository implements BlacklistRepository {
    private Map<UUID, Blacklist> storage = new ConcurrentHashMap<>();
    String PATH = "src/main/resources/";

    @Override
    public Optional<Blacklist> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void save(Blacklist blacklist) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(PATH + "blacklist.csv");
            oos = new ObjectOutputStream(fos);
            storage.put(blacklist.getBlacklistId(), blacklist);
            oos.writeObject(storage);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Blacklist> findAll() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(PATH + "blacklist.csv");
            ois = new ObjectInputStream(fis);
            storage = (ConcurrentHashMap<UUID, Blacklist>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(storage.values());
    }
}

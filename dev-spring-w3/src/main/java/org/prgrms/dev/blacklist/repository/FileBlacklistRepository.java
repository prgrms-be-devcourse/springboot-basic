package org.prgrms.dev.blacklist.repository;

import org.prgrms.dev.blacklist.domain.Blacklist;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository
public class FileBlacklistRepository implements BlacklistRepository {
    private static final String PATH = "src/main/resources/customer_blacklist.csv";
    private static final String USER_DIR = "user.dir";
    private static final String SPLIT_CODE = ":";
    private static final int UUID_INDEX = 0;
    private static final int NAME_INDEX = 1;

    private final File file;
    private final Map<UUID, Blacklist> blacklistStore;

    public FileBlacklistRepository() {
        this.file = new File(System.getProperty(USER_DIR), PATH);
        this.blacklistStore = init();
    }

    private Map<UUID, Blacklist> init() {
        Map<UUID, Blacklist> blackList = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] customerInfo = line.split(SPLIT_CODE);
                UUID customerId = UUID.fromString(customerInfo[UUID_INDEX]);
                String name = customerInfo[NAME_INDEX];
                Blacklist blacklist = new Blacklist(customerId, name);
                blackList.put(customerId, blacklist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackList;
    }

    @Override
    public List<Blacklist> findAllInBlackList() {
        return new ArrayList<>(blacklistStore.values());
    }
}

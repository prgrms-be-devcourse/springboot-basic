package org.prgrms.kdt.blacklist.repository;

import org.prgrms.kdt.blacklist.domain.Blacklist;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class BlacklistRepository {
    private final File file;
    public HashMap<UUID, Blacklist> blacklistHashMap;

    public BlacklistRepository(File file, Map<UUID, Blacklist> blacklistStore) {
        this.file = new File("src/main/resources/customer_blacklist.csv");
        this.blacklistHashMap = init();
    }

    private HashMap<UUID, Blacklist> init() {

        String line = "";
        HashMap<UUID, Blacklist> blackList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                UUID customerId = UUID.fromString(customerInfo[0]);
                String name = customerInfo[1];

                Blacklist blacklist = new Blacklist(customerId, name);
                blackList.put(customerId, blacklist);
            }
        } catch (IOException e) { e.printStackTrace(); }

        return blackList;
    }
}

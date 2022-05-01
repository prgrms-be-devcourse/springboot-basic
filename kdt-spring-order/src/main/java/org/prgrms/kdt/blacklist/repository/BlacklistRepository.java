package org.prgrms.kdt.blacklist.repository;

import org.prgrms.kdt.blacklist.domain.Blacklist;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class BlacklistRepository {
    // @Value("${datasource.blacklist.path}")
    // private String path;
    private final String path = "src/main/resources/datasource/customer_blacklist.csv";
    private final File file = new File(path);
    public HashMap<String, Blacklist> blacklistHashMap = new HashMap<String, Blacklist>();

    public void initBlacklistRepository() {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                String customerId = customerInfo[0];
                String name = customerInfo[1];

                Blacklist blacklist = new Blacklist(customerId, name);
                blacklistHashMap.put(customerId, blacklist);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}

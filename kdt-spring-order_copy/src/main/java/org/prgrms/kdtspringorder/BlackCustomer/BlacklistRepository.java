package org.prgrms.kdtspringorder.BlackCustomer;

import org.prgrms.kdtspringorder.FixedAmountVoucher;
import org.prgrms.kdtspringorder.PercentDiscountVoucher;
import org.prgrms.kdtspringorder.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class BlacklistRepository {
    private final static String path = "src/main/resources/blacklist.csv";
    private final File file;
    private Map<UUID, BlacklistCustomer> storage = new ConcurrentHashMap<>();

    public BlacklistRepository() {
        this.file = new File(path);
        this.storage = readFile();
    }

    public Map<UUID, BlacklistCustomer> readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] token = line.split(" ");

                for (int i = 0; i < token.length; i++) {
                    UUID blacklistId = UUID.fromString(token[0]); //id
                    String name = token[1]; //name
                    storage.put(blacklistId, new BlacklistCustomer(blacklistId, name));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storage;
    }

    public List<BlacklistCustomer> findAll() {
        return new ArrayList<>(storage.values());
    }
}

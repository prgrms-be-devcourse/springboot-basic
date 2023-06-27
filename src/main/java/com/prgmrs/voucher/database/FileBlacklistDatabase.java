package com.prgmrs.voucher.database;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FileBlacklistDatabase {
    String filename = "src/main/resources/blacklist.csv";

    public Map<UUID, String> getCache() {
        boolean append = Files.exists(Paths.get(filename));
        Map<UUID, String> cache = new HashMap<>();

        if (!append) {
            return cache;
        }

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] nextLine;
            reader.readNext();  // Skip header
            while ((nextLine = reader.readNext()) != null) {
                UUID uuid = UUID.fromString(nextLine[0]);
                String name = nextLine[1];
                cache.put(uuid, name);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }
}

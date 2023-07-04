package com.prgmrs.voucher.database;

import com.opencsv.CSVReader;
import com.prgmrs.voucher.exception.FileNotReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FileBlacklistDatabase {
    private static final Logger logger = LoggerFactory.getLogger(FileBlacklistDatabase.class);

    public Map<UUID, String> load(String filePath) {
        Map<UUID, String> storage = new HashMap<>();
        boolean append = Files.exists(Paths.get(filePath));

        if (!append) {
            return storage;
        }

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            reader.readNext();  // Skip header
            while ((nextLine = reader.readNext()) != null) {
                UUID uuid = UUID.fromString(nextLine[0]);
                String name = nextLine[1];
                storage.put(uuid, name);
            }
        } catch (Exception e) {
            logger.error("unexpected error occurred : ", e);
            throw new FileNotReadException("File was not read successfully.");
        }
        return storage;
    }
}

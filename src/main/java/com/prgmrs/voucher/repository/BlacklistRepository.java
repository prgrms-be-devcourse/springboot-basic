package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.database.FileBlacklistDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class BlacklistRepository {
    private static final String FILEPATH = "src/main/csv/blacklist.csv";
    private FileBlacklistDatabase fileBlacklistDatabase;

    @Autowired
    public BlacklistRepository(FileBlacklistDatabase fileBlacklistDatabase) {
        this.fileBlacklistDatabase = fileBlacklistDatabase;
    }

    public Map<UUID, String> findAll() {
        return fileBlacklistDatabase.load(FILEPATH);
    }
}

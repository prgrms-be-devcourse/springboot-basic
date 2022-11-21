package org.prgrms.voucherapplication.customer.repository;

import org.prgrms.voucherapplication.dto.ResponseBlacklist;
import org.prgrms.voucherapplication.voucher.service.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlackListFileRepository implements BlackListRepository {

    private final File blacklistFile;
    private final CsvFileService csvFileService;

    public BlackListFileRepository(@Value("${file-path.blacklist}") String blacklistFilePath, CsvFileService csvFileService) {
        this.blacklistFile = new File(blacklistFilePath);
        this.csvFileService = csvFileService;
    }

    @Override
    public List<ResponseBlacklist> findAll() {
        List<ResponseBlacklist> blacklistAll = new ArrayList<>();

        List<String> blacklistLines = csvFileService.readFileLines(blacklistFile);
        for (String blacklistString : blacklistLines) {
            String[] split = blacklistString.split(",");
            String idNum = split[0].replaceAll("\\D", "");
            BigInteger id = new BigInteger(idNum);
            String name = split[1];

            ResponseBlacklist blacklist = new ResponseBlacklist(id, name);
            blacklistAll.add(blacklist);
        }

        return blacklistAll;
    }
}

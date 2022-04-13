package org.prgms.kdtspringvoucher.blackList.repository;

import org.prgms.kdtspringvoucher.blackList.domain.BlackList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvFileBlackListRepository implements BlackListRepository {
    private static final Logger logger = LoggerFactory.getLogger(CsvFileBlackListRepository.class);

    private final ApplicationContext applicationContext;

    @Value("${black-list.csv-file-name}")
    private String FILENAME;

    @Value("${black-list.path}")
    private Path PATH;

    public CsvFileBlackListRepository (ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public List<BlackList> findAll() {
        try {
            Resource blackListResource = applicationContext.getResource("file:" + PATH);
            logger.info("Store save blackList data from {}", PATH);
            return Files.readAllLines(blackListResource.getFile().toPath())
                    .stream()
                    .map(this::getBlackListDataFromFile)
                    .toList();
        } catch (IOException e) {
            logger.error("Don't have the {}", FILENAME);
            e.printStackTrace();
            return null;
        }
    }

    private BlackList getBlackListDataFromFile(String readLine) {
        String[] split = readLine.split(",");
        UUID blackListId = UUID.fromString(split[0]);
        String blackListName = split[1];
        return new BlackList(blackListId, blackListName);
    }
}

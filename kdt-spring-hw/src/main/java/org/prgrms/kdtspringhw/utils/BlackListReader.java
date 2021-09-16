package org.prgrms.kdtspringhw.utils;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.blacklist.BlackListService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlackListReader implements CsvReader{
    private final String file;

    public BlackListReader(String file) throws IOException {
        this.file = file;
    }

    @Override
    public Map<UUID, BlackList> readCsv() throws IOException {
        var blackListMap = new ConcurrentHashMap<UUID,BlackList>();
        for(String line : Files.readAllLines(Path.of(file))){
            String [] token = line.split(",",-1);
            blackListMap.put(UUID.fromString(token[0]),
                        new BlackList(UUID.fromString(token[0])));
        }
        return blackListMap;
    }
}

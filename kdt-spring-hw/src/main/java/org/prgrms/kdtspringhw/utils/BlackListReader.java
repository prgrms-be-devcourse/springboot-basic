package org.prgrms.kdtspringhw.utils;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.blacklist.repository.CsvBlackListRepository;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlackListReader implements CsvReader{
    @Override
    public Map<UUID, BlackList> readCsv(Resource resource) throws IOException {
        var blackListMap = new ConcurrentHashMap<UUID,BlackList>();

        //여기서 직접 넣어주는게 나은가
        //var blackListRepository = new CsvBlackListRepository();

        File file = resource.getFile();
        for(String line : Files.readAllLines(file.toPath())){
            String [] token = line.split(",",-1);
            blackListMap.put(UUID.fromString(token[0]),
                        new BlackList(UUID.fromString(token[0])));
            //blackListRepository.insert(new BlackList(UUID.fromString(token[0])));
        }
        return blackListMap;
    }
}

package org.prgrms.kdt.blacklist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileBlackListRepository implements UserRepository {

    private final Map<UUID, BlackUser> memory = new ConcurrentHashMap<>();
    private static final String PATH = "src/main/resources/static/";

    @Override
    public List<BlackUser> findAll() {
        return new ArrayList<>(memory.values());
    }

    @PostConstruct
    public void FileToMem() {
        BlackListFileReader blackUserFileReader = new BlackListFileReader();
        memory.putAll(blackUserFileReader.readFile(PATH +"customer_blackList.csv"));
    }
}

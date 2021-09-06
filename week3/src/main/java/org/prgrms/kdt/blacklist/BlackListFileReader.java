package org.prgrms.kdt.blacklist;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlackListFileReader {

    public Map<UUID, BlackUser> readFile(String path){
        var map = new ConcurrentHashMap<UUID, BlackUser>();

        try {
            for (String line : Files.readAllLines(Path.of(path))) {
                String[] params = line.split(",");
                map.put(UUID.fromString(params[0]) , new BlackUser(UUID.fromString(params[0]), params[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

}

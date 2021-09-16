package org.prgrms.devcourse.util;

import org.prgrms.devcourse.domain.BlackUser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlackUserFileReader implements FileReader {
    @Override
    public Map<UUID, BlackUser> readFile(String filePath) {
        var blackUserMap = new ConcurrentHashMap<UUID, BlackUser>();

        try {
            for (String line : Files.readAllLines(Path.of(filePath))) {
                String[] params = line.split(",");
                blackUserMap.put(UUID.fromString(params[0])
                        , new BlackUser(UUID.fromString(params[0]), params[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return blackUserMap;
    }
}

package org.prgrms.kdt.util;

import org.prgrms.kdt.BlackCustomer.BlackCustomer;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BlackCustomerFileManager implements FileManager {
    @Override
    public Map<UUID, BlackCustomer> fileToMemory(String filePath) {
        var storage = new ConcurrentHashMap<UUID, BlackCustomer>();

        try {
            for (String line : Files.readAllLines(Path.of(filePath))) {
                String[] params = line.split(",");
                storage.put(UUID.fromString(params[0]),
                        BlackCustomer.builder()
                                .customerId(UUID.fromString(params[0]))
                                .name(params[1])
                                .phone(params[2])
                                .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return storage;
    }

    public void memoryToFile(String filePath, Map<UUID, BlackCustomer> blackCustomerMap) {
        try {
            Files.deleteIfExists(Path.of(filePath));
            Files.createFile(Path.of(filePath));

            for (Object voucher : blackCustomerMap.values())
                Files.writeString(Path.of(filePath), voucher.toString(), StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

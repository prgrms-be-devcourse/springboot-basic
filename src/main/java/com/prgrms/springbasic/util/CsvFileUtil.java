package com.prgrms.springbasic.util;

import com.prgrms.springbasic.domain.customer.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Component
public class CsvFileUtil {
    private static final Logger log = LoggerFactory.getLogger(CsvFileUtil.class);

    public static Map<UUID, Customer> loadCustomerFromFile(String filePath) {
        Map<UUID, Customer> customers = new ConcurrentHashMap<>();
        File file = new File(filePath);
        if (file.exists()) {
            return readCustomerFromFile(filePath);
        }
        return customers;
    }

    private static Map<UUID, Customer> readCustomerFromFile(String filePath) {
        Map<UUID, Customer> customers = new ConcurrentHashMap<>();
        try {
            String fileContent = Files.readString(Paths.get(filePath));
            Stream<String> lines = fileContent.lines();
            lines.forEach(line -> {
                String[] parts = line.split(",");
                UUID customerId = UUID.fromString(parts[0]);
                String customerName = parts[1];
                customers.put(customerId, new Customer(customerId, customerName));
            });
        } catch (IOException e) {
            log.error("The file does not exist. fileName : {}", filePath);
            throw new RuntimeException(e);
        }
        return customers;
    }
}

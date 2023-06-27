package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class FileCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> customerMap = new ConcurrentHashMap<>();

    @Value("${file.customer.path}")
    private String filePath;

    @Override
    public Map<UUID, Customer> getBlackList() {
        return Collections.unmodifiableMap(customerMap);
    }

    private void saveIfBlacklistedCustomer(String[] readLine) {
        UUID uuid = UUID.fromString(readLine[0]);
        CustomerType customerType = CustomerType.findCustomerType(readLine[1]);

        if (CustomerType.isBlacklistedCustomer(customerType)) {
            Customer customer = new Customer(uuid, CustomerType.BLACKLIST);

            customerMap.put(customer.getCustomerId(), customer);
        }
    }

    @PostConstruct
    public void loadingBlackListToMemory() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] readLine = line.split(",");
                saveIfBlacklistedCustomer(readLine);
            }

        } catch (Exception e) {
            log.error("error message: {}", e.getMessage());
        }
    }
}

package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@Profile("dev")
public class FileCustomerRepository {

    private final List<Customer> customerList = new ArrayList<>();

    @Value("${file.customer.path}")
    private String filePath;

    public List<Customer> getBlackList() {
        return Collections.unmodifiableList(customerList);
    }

    private void saveIfBlacklistedCustomer(String uuid, String name, String email, String customerType) {
        CustomerType type = CustomerType.findCustomerType(customerType);

        if (CustomerType.isBlacklistedCustomer(type)) {
            Customer customer = new Customer(UUID.fromString(uuid), name, email, CustomerType.BLACKLIST);

            customerList.add(customer);
        }
    }

    @PostConstruct
    public void loadingBlackListToMemory() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] readLine = line.split(",");
                saveIfBlacklistedCustomer(readLine[0], readLine[1], readLine[2], readLine[3]);
            }

        } catch (Exception e) {
            log.error("error message: {}", e.getMessage());
        }
    }
}

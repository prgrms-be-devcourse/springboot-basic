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

@Slf4j
public class FileCustomerRepository {

    private final List<Customer> customerList = new ArrayList<>();

    @Value("${file.customer.path}")
    private String filePath;

    public List<Customer> getBlackList() {
        return Collections.unmodifiableList(customerList);
    }

    private void saveIfBlacklistedCustomer(String uuid, String name, String email, String customerType) {
        CustomerType type = CustomerType.from(customerType);

        if (CustomerType.isBlacklistedCustomer(type)) {
            Customer customer = Customer.builder()
                    .customerId(UUID.fromString(uuid))
                    .customerName(name)
                    .customerEmail(email)
                    .customerType(CustomerType.BLACKLIST)
                    .build();

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

        } catch (IndexOutOfBoundsException e) {
            log.error("현재 파일에 저장된 열의 수가 맞지 않습니다.", e.getMessage());
        } catch (Exception e) {
            log.error("error message: {}", e.getMessage());
        }
    }

}

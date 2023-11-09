package com.programmers.springbootbasic.common.utils;

import com.programmers.springbootbasic.domain.customer.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class CsvFileManager {
    private final ResourceLoader resourceLoader;

    @Value("${repository.blacklist.fileName}")
    private String fileName;

    public CsvFileManager(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Map<UUID, Customer> loadDataFromCsv() {
        Map<UUID, Customer> customerMemory = new HashMap<>();
        Resource resource = resourceLoader.getResource(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(resource.getFile()), StandardCharsets.UTF_8))) {
            String tempData;
            while ((tempData = bufferedReader.readLine()) != null) {
                String[] rawCustomerData = tempData.split(",");
                Customer customer = Customer.builder()
                        .customerId(UUID.fromString(rawCustomerData[0]))
                        .name(rawCustomerData[1])
                        .build();
                customerMemory.put(customer.getCustomerId(), customer);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return customerMemory;
    }
}

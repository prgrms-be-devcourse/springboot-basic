package com.programmers.springbootbasic.repository.customer;

import com.programmers.springbootbasic.config.properties.FileProperties;
import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.util.CsvMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final CsvMapper csvMapper;
    private final Map<UUID, Customer> storage;

    public FileCustomerRepository(FileProperties fileProperties) {
        this.csvMapper = new CsvMapper();

        try {
            List<Customer> customers = csvMapper.readFile(new ClassPathResource(fileProperties.getCustomers()).getFile(), Customer::parseCsvLine);
            storage = customers.stream().collect(Collectors.toMap(Customer::getId, customer -> customer));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize customer repository", e);
        }
    }

    @Override
    public List<Customer> findAllBlacklisted() {
        return storage.values().stream()
                .filter(Customer::isBlacklisted)
                .collect(Collectors.toList());
    }
}

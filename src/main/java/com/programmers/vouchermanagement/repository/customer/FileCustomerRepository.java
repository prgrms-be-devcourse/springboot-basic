package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.config.properties.FileProperties;
import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;
import com.programmers.vouchermanagement.util.CsvMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> storage;

    public FileCustomerRepository(FileProperties fileProperties) {
        CsvMapper csvMapper = new CsvMapper();

        try {
            List<Customer> customers = csvMapper.readFile(new ClassPathResource(fileProperties.getCustomers()).getFile(), Customer::parseCsvLine);
            storage = customers.stream().collect(Collectors.toMap(Customer::getId, customer -> customer));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize customer repository", e);
        }
    }

    @Override
    public List<Customer> findAll(GetCustomersRequestDto request) {
        Stream<Customer> stream = storage.values().stream();

        if (request.isBlacklisted()) {
            stream = stream.filter(Customer::isBlacklisted);
        }

        return stream.collect(Collectors.toList());
    }
}

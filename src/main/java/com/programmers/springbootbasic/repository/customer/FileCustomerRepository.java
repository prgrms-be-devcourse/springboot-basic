package com.programmers.springbootbasic.repository.customer;

import com.programmers.springbootbasic.config.properties.FileProperties;
import com.programmers.springbootbasic.domain.customer.Customer;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileCustomerRepository implements CustomerRepository {
    private final FileProperties fileProperties;

    @Override
    public List<Customer> findAllBlacklisted() {
        String FILE_NAME = fileProperties.getCustomerBlacklist();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(FILE_NAME).getInputStream()))) {
            return reader.lines().skip(1).map(this::parseCsvLine).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error reading blacklist CSV file", e);
        }
    }

    private Customer parseCsvLine(String line) {
        String[] parts = line.split(",");
        return new Customer(UUID.fromString(parts[0]), parts[1]);
    }
}

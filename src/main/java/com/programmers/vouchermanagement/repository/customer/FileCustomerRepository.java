package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.config.properties.FileProperties;
import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.request.GetCustomersRequestDto;
import com.programmers.vouchermanagement.util.CsvMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Deprecated
@Repository
@Profile("file")
@Slf4j
public class FileCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> storage;

    public FileCustomerRepository(FileProperties fileProperties) {
        CsvMapper csvMapper = new CsvMapper();

        File file;

        try {
            file = new ClassPathResource(fileProperties.getCustomers()).getFile();
        } catch (IOException e) {
            String message = "Failed to get file path";
            log.error(message, e);
            throw new RuntimeException(message, e);
        }

        List<Customer> customers = csvMapper.readFile(file, Customer::parseCsvLine);
        storage = customers.stream().collect(Collectors.toMap(Customer::getId, customer -> customer));
    }

    @Override
    public void save(Customer customer) {
        unsupported();
    }

    @Override
    public void saveAll(List<Customer> customers) {
        unsupported();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll(GetCustomersRequestDto request) {
        Stream<Customer> stream = storage.values().stream();

        if (request.getBlacklisted()) {
            stream = stream.filter(Customer::isBlacklisted);
        }

        return stream.collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        unsupported();
    }

    private void unsupported() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

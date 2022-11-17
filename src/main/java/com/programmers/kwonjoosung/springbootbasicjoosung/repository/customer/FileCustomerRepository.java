package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;


import com.programmers.kwonjoosung.springbootbasicjoosung.config.FileCustomerBlackListProperties;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class FileCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);
    private final File customerBlackListCSVFile;

    FileCustomerRepository(FileCustomerBlackListProperties properties) {
        customerBlackListCSVFile = new File(properties.getFilePath() + properties.getFileName());
    }

    @Override
    public boolean insert(UUID customerId, String name) {
        try (Writer writer = new FileWriter(customerBlackListCSVFile, true)) {
            writer.write(customerId.toString() + "," + name);
            writer.flush();
            return true;
        } catch (IOException e) {
            logger.error("insert error message -> {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return Files.readAllLines(customerBlackListCSVFile.toPath())
                    .stream()
                    .map(Converter::toCustomer)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("findAll error message -> {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean update(UUID customerId, String name) {
        return false;
    }

    @Override
    public boolean delete(UUID customerId) {
        return false;
    }


    @Override
    public Customer findById(UUID customerId) {
        return null;
    }
}

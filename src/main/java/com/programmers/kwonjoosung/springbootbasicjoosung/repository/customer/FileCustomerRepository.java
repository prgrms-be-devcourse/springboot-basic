package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.config.FileCustomerBlackListProperties;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("file")
public class FileCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);
    private final File customerBlackListCSVFile;

    FileCustomerRepository(FileCustomerBlackListProperties fileCustomerBlackListProperties) {
        customerBlackListCSVFile = new File(fileCustomerBlackListProperties.getFilePath() + fileCustomerBlackListProperties.getFileName());
    }

    public List<Customer> findAllBlockCustomer() {
        try {
            List<String> customerBlackList = Files.readAllLines(customerBlackListCSVFile.toPath());
            return customerBlackList.stream().map(Converter::toCustomer).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("findAll error message -> {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean insert(Customer customer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Customer> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Customer customer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(UUID customerId) {
        throw new UnsupportedOperationException();
    }

}

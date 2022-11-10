package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;


import com.programmers.kwonjoosung.springbootbasicjoosung.config.FileCustomerBlackListProperties;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.utils.CustomerConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final File customerBlackListCSVFile;

    CustomerRepository(FileCustomerBlackListProperties fileCustomerBlackListProperties) {
        customerBlackListCSVFile = new File(fileCustomerBlackListProperties.getFilePath()+ fileCustomerBlackListProperties.getFileName());
    }

    public List<Customer> findAll() {
        try {
            List<String> customerBlackList = Files.readAllLines(customerBlackListCSVFile.toPath());
            return customerBlackList.stream()
                    .map(CustomerConverter::toCustomer)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("findAll error message -> {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}

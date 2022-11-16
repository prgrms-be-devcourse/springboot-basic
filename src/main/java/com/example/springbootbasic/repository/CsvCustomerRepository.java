package com.example.springbootbasic.repository;

import com.example.springbootbasic.config.AppConfiguration;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.parser.CsvCustomerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CsvCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CsvCustomerRepository.class);
    private final AppConfiguration appConfiguration;
    private final CsvCustomerParser csvParser = new CsvCustomerParser();

    @Autowired
    public CsvCustomerRepository(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public List<Customer> findAllCustomers() {
        Path csvPath = Paths.get(appConfiguration.getCustomerCsvResource());
        List<String> voucherTexts = Collections.emptyList();
        try {
            voucherTexts = Files.readAllLines(csvPath);
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucherTexts.stream()
                .map(csvParser::toCustomerFrom)
                .collect(Collectors.toList());
    }
}

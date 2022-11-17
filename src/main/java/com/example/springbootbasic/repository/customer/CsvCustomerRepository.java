package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.config.CsvProperties;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.parser.customer.CsvCustomerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("csv")
public class CsvCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CsvCustomerRepository.class);
    private final CsvProperties appConfiguration;
    private final CsvCustomerParser csvParser = new CsvCustomerParser();

    public CsvCustomerRepository(CsvProperties appConfiguration) {
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

    @Override
    public List<Customer> findCustomersByStatus(CustomerStatus status) {
        Path csvPath = Paths.get(appConfiguration.getCustomerCsvResource());
        List<String> voucherTexts = Collections.emptyList();
        try {
            voucherTexts = Files.readAllLines(csvPath);
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucherTexts.stream()
                .filter(voucherText -> voucherText.contains(status.name()))
                .map(csvParser::toCustomerFrom)
                .collect(Collectors.toList());
    }
}

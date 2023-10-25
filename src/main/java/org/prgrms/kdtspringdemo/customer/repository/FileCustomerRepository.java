package org.prgrms.kdtspringdemo.customer.repository;

import org.apache.commons.csv.CSVRecord;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.file.CsvFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.List;

@Repository
@PropertySource(value = "classpath:application.yml")
@Profile("dev")
public class FileCustomerRepository implements CustomerRepository{
    private CsvFileHandler csvFileHandler;
    @Value("${blackList_file}")
    private String blackListFilePath;

    public FileCustomerRepository() {
        this.csvFileHandler = new CsvFileHandler();
    }

    @Override
    public Optional<List<Customer>> getAllBlackList() throws IOException {
        List<Customer> customerList = new ArrayList<>();

        List<CSVRecord> data = csvFileHandler.readCSV(blackListFilePath);
        data.stream()
                .filter(line -> line.get("isBlack").equals("true"))
                .forEach(line -> {
                    UUID customerId = UUID.fromString(line.get("customerId"));
                    String name = line.get("name");
                    boolean isBlack = true;

                    Customer customer = new Customer(customerId, name, isBlack);
                    customerList.add(customer);
                });
        return Optional.of(customerList);
    }
}

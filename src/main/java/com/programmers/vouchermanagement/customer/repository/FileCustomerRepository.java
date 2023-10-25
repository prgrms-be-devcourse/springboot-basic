package com.programmers.vouchermanagement.customer.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.properties.AppProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;

@Repository
@Profile({"dev", "prod", "test"})
public class FileCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);
    private static final String COMMA_SEPARATOR = ", ";
    private static final String IO_EXCEPTION_LOG_MESSAGE = "Error raised while reading blacklist";

    private final String filePath;
    private final Map<UUID, Customer> customers;

    public FileCustomerRepository(AppProperties appProperties) {
        this.filePath = appProperties.getCustomerFilePath();
        this.customers = new HashMap<>();
        loadBlacklist();
    }

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        if (customers.isEmpty()) {
            return Collections.emptyList();
        }

        return null;
    }

    @Override
    public List<Customer> findBlackCustomers() {
        return customers.values()
                .stream()
                .filter(Customer::isBlack)
                .toList();
    }

    @Override
    @Profile("test")
    public void deleteAll() {
        customers.clear();
    }

    private void loadBlacklist() {
        List<Customer> blacklist = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip the first line
            String str;
            while ((str = br.readLine()) != null) {
                String[] line = str.split(COMMA_SEPARATOR);

                UUID blackCustomerId = UUID.fromString(line[0]);
                String name = line[1];

                Customer blackCustomer = new Customer(blackCustomerId, name, CustomerType.BLACK);

                blacklist.add(blackCustomer);
            }
        } catch (IOException e) {
            logger.error(IO_EXCEPTION_LOG_MESSAGE);
            throw new UncheckedIOException(e);
        }

        blacklist.forEach(blackCustomer -> customers.put(blackCustomer.getCustomerId(), blackCustomer));
    }
}

package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.properties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

@Repository
@Profile({"dev", "prod"})
public class CustomerFileRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerFileRepository.class);
    private static final String COMMA_SEPARATOR = ", ";
    private static final String IO_EXCEPTION_LOG_MESSAGE = "Error raised while reading blacklist";

    private final String filePath;
    private final Map<UUID, Customer> customers;

    public CustomerFileRepository(AppProperties appProperties) {
        this.filePath = appProperties.resources().path() + appProperties.domains().get("customer").fileName();
        this.customers = new HashMap<>();
        loadBlacklist();
    }

    @Override
    public List<Customer> findAllBlackCustomer() {
        return customers.values()
                .stream()
                .filter(Customer::isBlack)
                .toList();
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

                Customer blackCustomer = new Customer(blackCustomerId, name, true);

                blacklist.add(blackCustomer);
            }
        } catch (IOException e) {
            logger.error(IO_EXCEPTION_LOG_MESSAGE);
            throw new UncheckedIOException(e);
        }

        blacklist.forEach(blackCustomer -> customers.put(blackCustomer.getCustomerId(), blackCustomer));
    }
}

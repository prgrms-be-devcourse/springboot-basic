package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.Customer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    static final Map<UUID, Customer> customerMap = new ConcurrentHashMap<>();
    static final Map<UUID, Customer> blacklistMap = new ConcurrentHashMap<>();
    private static final Path FILE_PATH = Paths.get(System.getProperty("user.dir"), "src/main/resources/customer_blacklist.csv");
    private static final String DELIMITER = ",";
    private static final int INDEX_UUID = 0;
    private static final int INDEX_NAME = 1;

    @PostConstruct
    public static void fileRead() {
        try {
            final List<String> lines = Files.readAllLines(FILE_PATH);
            for (final var line : lines) {
                final String[] customerBlacklist = line.split(DELIMITER);
                final UUID customerId = UUID.fromString(customerBlacklist[INDEX_UUID]);
                final String customerName = customerBlacklist[INDEX_NAME];

                blacklistMap.put(customerId, new Customer(customerId, customerName, true));
            }
            System.out.println("Blacklist File Read Complete!");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer insert(final Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public List<Customer> findAllCustomer() {
        return new ArrayList<Customer>(customerMap.values());
    }

    @Override
    public List<Customer> findAllBlacklist() {
        return new ArrayList<Customer>(blacklistMap.values());
    }
}

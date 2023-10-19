package com.programmers.vouchermanagement.customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private static final String BLACKLIST_FILE_PATH = "src/main/resources/blacklist.csv";
    private final Map<UUID, Customer> customers;

    public FileCustomerRepository() {
        this.customers = new HashMap<>();
        loadBlacklist();
    }

    @Override
    public List<Customer> findBlackCustomers() {
        return customers.values()
                .stream()
                .filter(Customer::isBlack)
                .toList();
    }

    private void loadBlacklist() {
        List<Customer> blacklist = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(BLACKLIST_FILE_PATH))) {
            br.readLine(); // skip the first line
            String str;
            while ((str = br.readLine()) != null) {
                String[] line = str.split(", ");

                UUID blackCustomerId = UUID.fromString(line[0]);
                String name = line[1];

                Customer blackCustomer = new Customer(blackCustomerId, name, true);

                blacklist.add(blackCustomer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: add logger
        }

        blacklist.forEach(blackCustomer -> customers.put(blackCustomer.getCustomerId(), blackCustomer));
    }
}

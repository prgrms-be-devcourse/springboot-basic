package com.programmers.vouchermanagement.customer.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.properties.AppProperties;

@Repository
@Profile({"dev", "prod"})
public class FileCustomerRepository implements CustomerRepository {
    private static final String COMMA_SEPARATOR = ", ";
    private final String filePath;
    private final Map<UUID, Customer> customers;

    public FileCustomerRepository(AppProperties appProperties) {
        this.filePath = appProperties.getResources().getPath() + appProperties.getDomains().get("customer.file-name");
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
            e.printStackTrace();
            //TODO: add logger
        }

        blacklist.forEach(blackCustomer -> customers.put(blackCustomer.getCustomerId(), blackCustomer));
    }
}

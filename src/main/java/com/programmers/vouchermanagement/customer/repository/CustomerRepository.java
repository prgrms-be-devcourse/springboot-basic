package com.programmers.vouchermanagement.customer.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;

public interface CustomerRepository {
    String COMMA_SEPARATOR = ", ";

    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerId);
    List<Customer> findBlackCustomers();
    void deleteById(UUID customerId);
    void deleteAll();
    void loadBlacklistToStorage();

    default List<Customer> loadBlacklist(String blacklistFilePath) {
        List<Customer> blacklist = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(blacklistFilePath))) {
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
            throw new UncheckedIOException(e);
        }

        return blacklist;
    }
}

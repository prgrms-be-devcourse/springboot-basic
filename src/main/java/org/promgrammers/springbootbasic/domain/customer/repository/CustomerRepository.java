package org.promgrammers.springbootbasic.domain.customer.repository;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.util.FileConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {

    @Value("${blackListStoragePath}")
    private final String blackListStoragePath;
    private static final Path filePath = Paths.get("src/main/resources/storage/blacklist.csv");

    public CustomerRepository(@Value("${blackListStoragePath}") String blackListStoragePath) {
        this.blackListStoragePath = blackListStoragePath;
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Customer customer = FileConverter.parseCustomerFromLine(line);
                customers.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }
}




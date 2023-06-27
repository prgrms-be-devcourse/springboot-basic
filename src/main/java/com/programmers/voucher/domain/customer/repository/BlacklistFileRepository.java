package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.global.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BlacklistFileRepository implements BlacklistRepository {
    private final File file;

    public BlacklistFileRepository(@Value("${file.blacklist.path}") String filePath,
                                   ResourceLoader resourceLoader) {
        Resource resource = resourceLoader.getResource("file:" + filePath);
        try {
            this.file = resource.getFile();
        } catch (IOException e) {
            throw new DataAccessException("File not found.");
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
        ) {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                Customer customer = csvToCustomer(nextLine);
                customers.add(customer);
            }
        } catch (IOException e) {
            throw new DataAccessException("Read fail.");
        }
        return customers;
    }

    private Customer csvToCustomer(String nextLine) {
        String[] customerInfo = nextLine.split(",");
        UUID customerId = UUID.fromString(customerInfo[0]);
        String name = customerInfo[1];
        Customer customer = new Customer(customerId, name);
        return customer;
    }
}

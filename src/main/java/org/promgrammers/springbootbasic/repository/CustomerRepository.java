package org.promgrammers.springbootbasic.repository;

import org.promgrammers.springbootbasic.domain.Customer;
import org.promgrammers.springbootbasic.util.Converter;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {

    private static final File filePath = new File("src/main/resources/storage/blacklist.csv");

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = Converter.parseCustomerFromLine(line);
                customers.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }
}




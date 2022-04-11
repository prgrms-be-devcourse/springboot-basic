package com.prgms.management.customer.repository;

import com.prgms.management.customer.entity.Customer;
import com.prgms.management.customer.entity.CustomerType;
import com.prgms.management.customer.exception.CustomerException;
import com.prgms.management.customer.exception.CustomerListEmptyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileBlackCustomerRepository implements BlackCustomerRepository {
    private final File file;

    public FileBlackCustomerRepository(@Value("${database.file.black-list}") String filename) {
        this.file = new File(filename);
    }

    @Override
    public List<Customer> findAll() throws CustomerException {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String item;
            while ((item = bufferedReader.readLine()) != null) {
                String[] array = item.split(",");
                customers.add(new Customer(CustomerType.BLACK, UUID.fromString(array[0]), array[1]));
            }
        } catch (IOException e) {
            throw new CustomerListEmptyException();
        }
        return customers;
    }
}

package org.prgrms.kdtspringvoucher.customer.repository;

import org.prgrms.kdtspringvoucher.customer.entity.Customer;
import org.prgrms.kdtspringvoucher.customer.entity.CustomerType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class FileBlackCustomerRepository implements BlackCustomerRepository {
    private final File file;

    public FileBlackCustomerRepository(@Value("${database.file.black-list}") String filename) {
        this.file = new File(filename);
    }

    @Override
    public List<Customer> listAll() {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String item;
            while ((item = bufferedReader.readLine()) != null) {
                String[] array = item.split(",");
                customers.add(new Customer(CustomerType.BLACK, UUID.fromString(array[0]), array[1]));
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
        return customers;
    }
}

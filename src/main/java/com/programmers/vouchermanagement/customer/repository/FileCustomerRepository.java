package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.exception.FileIOException;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.mapper.CustomerMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile({"file", "memory"})
public class FileCustomerRepository implements CustomerRepository {

    private static final File FILE = new File(System.getProperty("user.dir") + "/src/main/resources/customer_blacklist.csv");

    public List<Customer> findAllBlack() {

        List<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {

            String data;

            while ((data = br.readLine()) != null) {

                String[] singleData = data.split(",");

                Customer customer = CustomerMapper.toEntity(singleData);
                customers.add(customer);
            }
        } catch (FileNotFoundException e) {
            throw new FileIOException("File not found. ");

        } catch (IOException e) {
            throw new FileIOException("Customer not read due to file issue. ");
        }

        return customers;
    }
}

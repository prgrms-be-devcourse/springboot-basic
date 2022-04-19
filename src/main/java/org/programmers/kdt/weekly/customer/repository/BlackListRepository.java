package org.programmers.kdt.weekly.customer.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.customer.Customer;
import org.programmers.kdt.weekly.customer.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BlackListRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(BlackListRepository.class);
    private final File file = new File("customer_blackList.csv");

    @Override
    public Customer insert(Customer customer) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(customer.getCustomerId() + "," + customer.getCustomerName());
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("insert IOException  error", e);
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> blackList = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split(",");
                blackList.add(new Customer(UUID.fromString(arr[0]), arr[1], CustomerType.BLACK));
            }
        } catch (IOException | NullPointerException e) {
            logger.error("findAll() IOException  error ", e);
        }

        return List.copyOf(blackList);
    }
}
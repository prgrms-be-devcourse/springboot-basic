package org.programmers.kdt.weekly.customer.repository;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.customer.Customer;
import org.programmers.kdt.weekly.customer.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class BlackListRepository implements CustomerRepository {

    private final static Logger logger = LoggerFactory.getLogger(BlackListRepository.class);
    private final Resource FILE_PATH = new ClassPathResource("customer_blacklist.csv");

    @Override
    public void insert(UUID customerId, Customer customer) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH.getURL().getPath());
            fileOutputStream.write(
                (customer.getCustomerId() + "," + customer.getCustomerName()).getBytes());
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int count() {
        int size = 0;
        try {
            InputStream inputStream = FILE_PATH.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.readLine() != null) {
                size++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        String line;
        try {
            InputStream inputStream = FILE_PATH.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split(",");
                customerList.add(new Customer(UUID.fromString(arr[0]), arr[1], CustomerType.BLACK));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}

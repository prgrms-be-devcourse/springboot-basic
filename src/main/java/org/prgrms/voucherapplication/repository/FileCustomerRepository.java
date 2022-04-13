package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.BlackListCustomer;
import org.prgrms.voucherapplication.entity.Customer;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private static final String PATH = "data/customer_blacklist.csv";

    @Override
    public List<Customer> findAll() throws IOException {
        File file = new File(PATH);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<Customer> customerList = new ArrayList<>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] customerInfo = line.split(",");
            customerList.add(restoreCustomer(customerInfo[0], customerInfo[1]));
        }
        reader.close();
        return customerList;
    }

    private Customer restoreCustomer(String id, String customerName) {
        return new BlackListCustomer(Long.valueOf(id), customerName);
    }
}

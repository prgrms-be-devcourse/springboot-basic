package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.console.Reader;
import com.prgrms.vouchermanager.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Repository
public class CustomerFileRepository implements CustomerRepository {
    private final Map<Integer, Customer> customerMap = new HashMap<>();
    private final Scanner fc;
    @Autowired
    public CustomerFileRepository(Reader reader) {
        this.fc = reader.fc;
        fileToMap();
    }

    @Override
    public List<Customer> blacklist() {
        return customerMap
                .values()
                .stream().toList();
    }

    private void fileToMap() {
        while(fc.hasNextLine()) {
            String[] split = fc.nextLine().split(",");
            Customer customer
                    = new Customer(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2]));
            customerMap.put(Integer.parseInt(split[0]), customer);
        }
    }
}

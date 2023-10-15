package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.console.Reader;
import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.exception.FileIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerFileRepository implements CustomerRepository {
    private final Map<Integer, Customer> customerMap = new HashMap<>();
    private final BufferedReader bf;
    @Autowired
    public CustomerFileRepository(Reader reader) {
        this.bf = reader.bf;
        fileToMap();
    }

    @Override
    public List<Customer> blacklist() {
        return customerMap
                .values()
                .stream().toList();
    }

    private void fileToMap() {
        String line = "";

        while(true) {
            try {
                if ((line = bf.readLine()) == null) break;
            } catch (IOException e) {
                throw new FileIOException();
            }
            String[] split = line.split(",");
            Customer customer
                    = new Customer(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2]));
            customerMap.put(Integer.parseInt(split[0]), customer);
        }
    }
}

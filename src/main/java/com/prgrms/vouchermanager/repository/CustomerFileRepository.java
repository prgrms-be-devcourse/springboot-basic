package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.io.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerFileRepository implements CustomerRepository {
    private final Map<Integer, Customer> customerMap = new HashMap<>();
    @Autowired
    public CustomerFileRepository(@Value("${csv.blacklist}") String fileName) {
        FileIO fileIO = new FileIO(fileName);
        fileIO.fileToCustomerMap(customerMap);
    }

    @Override
    public List<Customer> blacklist() {
        return customerMap
                .values()
                .stream().toList();
    }
}

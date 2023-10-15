package com.prgrms.springbasic.domain.customer.repository;


import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.util.CsvFileUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class CustomerRepository {
    private static final String BLACKLIST_FILE = "customer_blacklist.csv";

    public List<Customer> findAllBlackList() {
        Map<UUID, Customer> blackListCustomers = CsvFileUtil.loadCustomerFromFile(BLACKLIST_FILE);
        return blackListCustomers.values()
                .stream()
                .toList();
    }
}

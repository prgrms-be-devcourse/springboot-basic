package com.prgrms.springbasic.domain.customer.repository;


import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.util.CsvFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class CustomerRepository {
    @Value("${repository.customer.blacklist_filePath}")
    private String filePath;

    public List<Customer> findAllBlackList() {
        Map<UUID, Customer> blackListCustomers = CsvFileUtil.loadCustomerFromFile(filePath);
        return blackListCustomers.values()
                .stream()
                .toList();
    }
}

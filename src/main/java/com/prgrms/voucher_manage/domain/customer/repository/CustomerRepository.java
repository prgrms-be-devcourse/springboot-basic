package com.prgrms.voucher_manage.domain.customer.repository;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.util.CustomerFileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    private final String PATH = System.getProperty("user.dir") + "/src/main/resources/customer_blacklist.csv";
    private final CustomerFileManager customerFileManager;

    public List<Customer> findAll() {
        Map<UUID, Customer> storage = customerFileManager.loadCustomerData(PATH);
        return storage.values().stream().toList();
    }
}

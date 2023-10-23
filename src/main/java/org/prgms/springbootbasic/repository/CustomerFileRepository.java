package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.domain.customer.CustomerCsvFileManager;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerFileRepository implements CustomerRepository {
    private final CustomerCsvFileManager customerCsvFileManager;

    public CustomerFileRepository(CustomerCsvFileManager customerCsvFileManager) {
        this.customerCsvFileManager = customerCsvFileManager;
    }

    @Override
    public List<Customer> findBlackAll() {
        return customerCsvFileManager.readBlack();
    }
}

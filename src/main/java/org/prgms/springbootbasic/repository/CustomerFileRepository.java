package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.common.file.CustomerCsvFileManager;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"dev", "prod"})
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

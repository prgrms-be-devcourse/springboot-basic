package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.domain.customer.CsvCustomerFileManager;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerFileRepository implements CustomerRepository {
    private final CsvCustomerFileManager csvCustomerFileManager;

    public CustomerFileRepository(CsvCustomerFileManager csvCustomerFileManager) {
        this.csvCustomerFileManager = csvCustomerFileManager;
    }

    @Override
    public List<Customer> findBlackAll() {
        return csvCustomerFileManager.readBlack();
    }
}

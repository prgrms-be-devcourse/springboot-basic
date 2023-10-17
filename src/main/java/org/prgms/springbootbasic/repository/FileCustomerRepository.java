package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.common.CsvCustomerFileManager;
import org.prgms.springbootbasic.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final CsvCustomerFileManager csvCustomerFileManager;

    public FileCustomerRepository(CsvCustomerFileManager csvCustomerFileManager) {
        this.csvCustomerFileManager = csvCustomerFileManager;
    }

    @Override
    public List<Customer> findBlackAll() {
        return csvCustomerFileManager.readBlack();
    }
}

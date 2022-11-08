package org.prgrms.vouchermanagement.customer.repository;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlackListCustomerRepository implements CustomerRepository {

    private final String path;

    public BlackListCustomerRepository(@Value("${repository.file.blacklist.path}") String path) {
        this.path = path;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    private Customer createCustomer(String[] customerInfos) {
        return null;
    }
}

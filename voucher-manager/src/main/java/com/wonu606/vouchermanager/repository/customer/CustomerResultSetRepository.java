package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import java.util.List;
import java.util.Optional;

public interface CustomerResultSetRepository {

    Customer save(Customer customer);

    Optional<CustomerResultSet> findByEmailAddress(String emailAddress);

    List<CustomerResultSet> findAll();

    void deleteByEmailAddress(String emailAddress);

    void deleteAll();

}

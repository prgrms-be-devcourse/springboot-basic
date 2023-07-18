package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.List;
import java.util.Optional;

public interface CustomerResultSetRepository {

    Customer save(Customer customer);

    Optional<CustomerResultSet> findByEmailAddress(Email email);

    List<CustomerResultSet> findAll();

    void deleteByEmailAddress(Email email);

    void deleteAll();

    List<CustomerResultSet> findAllByEmailAddresses(List<Email> emails);
}

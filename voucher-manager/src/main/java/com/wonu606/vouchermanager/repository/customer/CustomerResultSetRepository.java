package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import java.util.List;
import java.util.Optional;

public interface CustomerResultSetRepository {

    Customer save(Customer customer);

    Optional<CustomerResultSet> findByEmailAddress(EmailAddress emailAddress);

    List<CustomerResultSet> findAll();

    void deleteByEmailAddress(EmailAddress emailAddress);

    void deleteAll();

    List<CustomerResultSet> findAllByEmailAddresses(List<EmailAddress> emailAddresses);
}

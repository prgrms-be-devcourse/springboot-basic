package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByEmailAddress(EmailAddress emailAddress);

    List<Customer> findAll();

    List<Customer> findAllByEmailAddresses(List<EmailAddress> emailAddresses);

    void deleteByEmailAddress(EmailAddress emailAddress);

    void deleteAll();
}

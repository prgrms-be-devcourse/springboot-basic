package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByEmailAddress(Email email);

    List<Customer> findAll();

    List<Customer> findAllByEmailAddresses(List<Email> emails);

    void deleteByEmailAddress(Email email);

    void deleteAll();
}

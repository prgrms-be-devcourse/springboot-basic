package com.prgrms.vouchermanagement.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Long save(Customer customer);

    void update(Customer customer);

    void remove(Long customerId);

    List<Customer> findAll();

    Optional<Customer> findById(Long customerID);

    List<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findCustomerByVoucher(Long voucherId);
}

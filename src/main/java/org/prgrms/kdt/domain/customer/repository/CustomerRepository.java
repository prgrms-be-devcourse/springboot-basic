package org.prgrms.kdt.domain.customer.repository;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    List<Customer> findAll();

    UUID save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByVoucherId(UUID voucherId);

    List<Customer> findByCustomerType(CustomerType customerType);

    int updateById(Customer customer);

    void deleteById(UUID customerId);

    void deleteAll();
}

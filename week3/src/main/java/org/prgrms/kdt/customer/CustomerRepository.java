package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerID);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    List<Customer> findCustomersByWalletIds(List<byte[]> vouchers);
}

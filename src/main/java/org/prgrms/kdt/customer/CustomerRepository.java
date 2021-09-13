package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:08 오후
 */
public interface CustomerRepository {

    void insert(Customer customer);

    void update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findCustomersByVoucherId(UUID voucherId);

    void deleteAll();

    int count();
}

package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerManager {

    void save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(long id);

    void update(Customer customer);

    void deleteById(long id);

    void deleteAll();
}

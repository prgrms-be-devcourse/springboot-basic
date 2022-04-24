package org.programs.kdt.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);


    int count();

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    List<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    List<Customer> findByType(String type);

    void deleteByEmail(String email);

    boolean existEmail(String email);

    boolean existId(UUID customerId);
}

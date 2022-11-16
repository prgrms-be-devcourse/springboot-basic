package org.programmers.weekly.mission.domain.customer.repository;

import org.programmers.weekly.mission.domain.customer.model.BlackCustomer;
import org.programmers.weekly.mission.domain.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<BlackCustomer> getBlackList();
    Customer insert(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(UUID customerId);
    Customer update(Customer customer);
    void deleteAll();
    void delete(UUID customerId);
}

package team.marco.voucher_management_system.repository.custromer;

import team.marco.voucher_management_system.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerId);
}

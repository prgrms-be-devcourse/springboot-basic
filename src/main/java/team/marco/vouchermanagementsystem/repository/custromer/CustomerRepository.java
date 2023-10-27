package team.marco.vouchermanagementsystem.repository.custromer;

import team.marco.vouchermanagementsystem.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerId);
}

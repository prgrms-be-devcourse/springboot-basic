package team.marco.voucher_management_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import team.marco.voucher_management_system.model.Customer;

public interface CustomerRepository {
    int create(Customer customer);

    List<Customer> findAll();

    int update(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findByName(String name);

    List<Customer> findByEmail(String email);
}

package prgms.springbasic.NomalCustomer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Customer join(Customer customer);

    List<Customer> getCustomerList();

    Optional<Customer> findByName(String name);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    void deleteAll();
}

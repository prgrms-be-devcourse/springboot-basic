package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.util.UUIDUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> customerDatabase = new ConcurrentHashMap<>();

    @Override
    public List<Customer> findAllBlacklistedCustomers() {
        return customerDatabase.values()
                .stream()
                .filter(Customer::isBlacklisted)
                .toList();
    }

    @Override
    public Customer save(Customer customer) {
        customerDatabase.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(customerDatabase.get(customerId));
    }

    @Override
    public boolean update(Customer customer) {
        int updatedRow = customerDatabase.containsKey(customer.getId()) ? 1 : 0;
        if (updatedRow == 1) customerDatabase.put(customer.getId(), customer);
        return updatedRow == 1;
    }

    @PostConstruct
    public void init() {
        Customer customer1 = Customer.createCustomer(UUIDUtil.generateRandomUUID(), "not blacklist ogu", false);
        Customer customer2 = Customer.createCustomer(UUIDUtil.generateRandomUUID(), "blacklist platypus", true);
        customerDatabase.put(customer1.getId(), customer1);
        customerDatabase.put(customer2.getId(), customer2);
    }
}

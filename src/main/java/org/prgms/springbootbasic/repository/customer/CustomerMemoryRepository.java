package org.prgms.springbootbasic.repository.customer;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"local"})
public class CustomerMemoryRepository implements CustomerRepository { // 추후 구현
    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findBlackAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Customer> deleteById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}

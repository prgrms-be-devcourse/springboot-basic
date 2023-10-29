package org.prgms.springbootbasic.repository.customer;

import org.prgms.springbootbasic.common.file.CustomerCsvFileManager;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"test"})
public class CustomerFileRepository implements CustomerRepository { // 추후 구현
    private final CustomerCsvFileManager customerCsvFileManager;

    public CustomerFileRepository(CustomerCsvFileManager customerCsvFileManager) {
        this.customerCsvFileManager = customerCsvFileManager;
    }

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
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public List<Customer> findBlackAll() {
        return customerCsvFileManager.readBlack();
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

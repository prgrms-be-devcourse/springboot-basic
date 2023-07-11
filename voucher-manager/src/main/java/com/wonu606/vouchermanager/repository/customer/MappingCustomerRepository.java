package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import com.wonu606.vouchermanager.domain.customer.EmailAddress;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MappingCustomerRepository implements CustomerRepository {

    private final CustomerResultSetRepository customerResultSetRepository;

    public MappingCustomerRepository(CustomerResultSetRepository customerResultSetRepository) {
        this.customerResultSetRepository = customerResultSetRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerResultSetRepository.save(customer);
    }

    @Override
    public Optional<Customer> findByEmailAddress(String emailAddress) {
        return customerResultSetRepository.findByEmailAddress(emailAddress)
                .map(this::convertResultSetToEntity);
    }

    @Override
    public List<Customer> findAll() {
        return customerResultSetRepository.findAll()
                .stream().map(this::convertResultSetToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByEmailAddress(String emailAddress) {
        customerResultSetRepository.deleteByEmailAddress(emailAddress);
    }

    @Override
    public void deleteAll() {
        customerResultSetRepository.deleteAll();
    }

    private Customer convertResultSetToEntity(CustomerResultSet customerResultSet) {
        return new Customer(
                new EmailAddress(customerResultSet.getEmailAddress()),
                customerResultSet.getNickname());
    }
}

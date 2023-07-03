package me.kimihiqq.vouchermanagement.domain.customer.service;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.dto.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Customer createCustomer(CustomerDto customerDto);
    List<Customer> listCustomers();
    Optional<Customer> findCustomerById(UUID customerId);
    void deleteCustomerById(UUID customerId);
    void addVoucherToCustomer(UUID customerId, UUID voucherId);
    void removeVoucherFromCustomer(UUID customerId, UUID voucherId);

    void updateCustomerStatus(Customer customer);

}

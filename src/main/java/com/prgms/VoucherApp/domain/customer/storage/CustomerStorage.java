package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerStorage {

    List<Customer> findAll();

    void save(Customer customer);

    Optional<Customer> findById(UUID id);

    void updateStatus(UUID customerId, CustomerStatus status);

    void deleteById(UUID id);

    List<Customer> findByCustomerStatus(CustomerStatus customerStatus);
}

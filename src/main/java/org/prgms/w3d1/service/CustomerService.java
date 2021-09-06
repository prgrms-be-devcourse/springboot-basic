package org.prgms.w3d1.service;

import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.repository.CustomerRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    @Transactional
    void createCustomers(List<Customer> customers);

    Optional<Customer> getCustomer(UUID customerId);

    void saveCustomer(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);

    Customer createCustomer(String name, String email);

    Customer updateCustomerByNameAndEmail(UUID customerId, String name, String email);

    void deleteCustomer(UUID customerId);
}

package org.prgms.w3d1.service;

import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.repository.CustomerRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Customer createCustomer(String name, String email) {
        var customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return customerRepository.insert(customer);
    }

    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public Optional<Customer> getCustomer(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.insert(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        var voucher = voucherRepository.findById(voucherId);

        if (voucher.isEmpty()) {
            throw new RuntimeException("There is no Voucher :" + voucherId.toString());
        }

        return customerRepository.findById(voucher.get().getCustomerId());
    }

    @Override
    public Customer updateCustomerByNameAndEmail(UUID customerId, String name, String email) {
        return customerRepository.update(new Customer(customerId, name, email, LocalDateTime.now()));
    }
}

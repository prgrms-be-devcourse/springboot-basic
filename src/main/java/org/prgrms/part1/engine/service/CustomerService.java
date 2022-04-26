package org.prgrms.part1.engine.service;

import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.repository.CustomerRepository;
import org.prgrms.part1.exception.VoucherException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final VoucherService voucherService;

    public CustomerService(CustomerRepository customerRepository, VoucherService voucherService) {
        this.customerRepository = customerRepository;
        this.voucherService = voucherService;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer getCustomerById(UUID customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.orElseThrow(() -> new VoucherException("Invalid customer id."));
    }

    @Transactional
    public Customer createCustomer(String name, String email) {
        return insertCustomer(new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().withNano(0)));
    }

    @Transactional
    public Customer insertCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }

    @Transactional
    public void removeCustomer(Customer customer) {
        List<Voucher> ownedVouchers = voucherService.getVouchersByCustomer(customer);
        ownedVouchers.forEach(voucherService::removeVoucher);
        customerRepository.deleteById(customer.getCustomerId());
    }
}

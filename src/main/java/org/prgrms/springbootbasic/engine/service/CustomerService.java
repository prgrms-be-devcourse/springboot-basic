package org.prgrms.springbootbasic.engine.service;

import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.engine.repository.CustomerRepository;
import org.prgrms.springbootbasic.exception.RecordNotFoundException;
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
        return customer.orElseThrow(() -> new RecordNotFoundException("Invalid customer id.", ErrorCode.CUSTOMER_NOT_FOUND));
    }

    @Transactional
    public List<Voucher> getVouchersByCustomer(Customer customer) {
        return voucherService.getVouchersByCustomer(customer);
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
    public Customer updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    @Transactional
    public void removeCustomer(Customer customer) {
        List<Voucher> ownedVouchers = voucherService.getVouchersByCustomer(customer);
        ownedVouchers.forEach(voucherService::removeVoucher);
        customerRepository.deleteById(customer.getCustomerId());
    }
}

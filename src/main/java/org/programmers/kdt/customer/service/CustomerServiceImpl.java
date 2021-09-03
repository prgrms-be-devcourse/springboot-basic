package org.programmers.kdt.customer.service;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.repository.CustomerRepository;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private VoucherService voucherService;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer signUp(UUID customerId, String name, String email) {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(customerId, name, email, now, now);
        customerRepository.insert(customer);
        logger.info("New Customer has been successfully added to customer list");
        return customer;
    }

    @Override
    public Optional<Customer> findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer addToBlacklist(Customer customer) {
        Customer newBlacklistedCustomer = customerRepository.registerToBlacklist(customer);
        logger.info("Customer({}) has been added into blacklist", customer.getCustomerId());
        return newBlacklistedCustomer;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllBlacklistCustomer() {
        return customerRepository.findAllBlacklistCustomer();
    }

    @Override
    public void removeCustomer(UUID customerId) {
        customerRepository.deleteCustomer(customerId);
    }

    @Override
    public boolean isOnBlacklist(Customer customer) {
        return customerRepository.findAllBlacklistCustomer().contains(customer);
    }

    @Override
    public String getPrintFormat(Customer customer) {
        return MessageFormat
                .format("<< Customer Information >>\nCustomer ID : {0}\nCustomer Name : {1}\nCustomer Email : {2}\nLast Login At : {3}\nSign Up At : {4}",
                        customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt());
    }

    @Override
    public boolean addVoucherToCustomer(Customer customer, Voucher voucher) {
        return voucher.equals(voucherService.addOwner(customer, voucher));
    }

    @Override
    public void removeVoucherFromCustomer(Customer customer, UUID voucherId) {
        voucherService.removeOwner(customer, voucherId);
    }

    @Override
    public Optional<Customer> findByVoucher(UUID voucherId) {
        Optional<UUID> customerId = voucherService.findCustomerIdHoldingVoucherOf(voucherId);
        if (customerId.isEmpty()) {
            return Optional.empty();
        }
        return customerRepository.findById(customerId.get());

    }

    @Override
    public List<Voucher> getAllVoucherOf(Customer customer) {
        return voucherService.getAllVouchersBelongsToCustomer(customer);
    }
}

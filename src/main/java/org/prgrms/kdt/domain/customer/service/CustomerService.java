package org.prgrms.kdt.domain.customer.service;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.repository.CustomerRepository;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final VoucherService voucherService;

    public CustomerService(CustomerRepository customerRepository, VoucherService voucherService) {
        this.customerRepository = customerRepository;
        this.voucherService = voucherService;
    }

    public List<Customer> getBlackCustomers() {
        List<Customer> blackCustomers = customerRepository.findByType(CustomerType.BLACK_LIST);
        logger.info("Get blackList customers size: {}", blackCustomers.size());
        return blackCustomers;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        logger.info("Get all stored customers size: {}", customers.size());
        return customers;
    }

    public List<Customer> getCustomersByVoucherTypeAndDate(VoucherType voucherType, LocalDate date) {
        List<Voucher> vouchers = voucherService.getVoucherByTypeAndDate(voucherType, date);
        List<Customer> customers = new ArrayList<>();
        vouchers.forEach(voucher -> customerRepository.findByVoucherId(voucher.getVoucherId())
                .ifPresent(customers::add));
        logger.info("Get customers by voucher type and date size: {}", customers.size());
        return customers;
    }

    @Transactional
    public UUID createCustomer(String name, String email, CustomerType customerType) {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(UUID.randomUUID(), name, email, customerType, now, now);
        UUID customerId = customerRepository.save(customer);
        logger.info("create Customer Id: {}", customer.getCustomerId());
        return customerId;
    }

}

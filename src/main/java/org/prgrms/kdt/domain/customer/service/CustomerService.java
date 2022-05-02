package org.prgrms.kdt.domain.customer.service;

import org.prgrms.kdt.domain.customer.exception.CustomerDataException;
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
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.domain.common.exception.ExceptionType.*;

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

    @Transactional
    public UUID save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getBlackCustomers() {
        return customerRepository.findAllByType(CustomerType.BLACK_LIST);
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomersByVoucherTypeAndDate(VoucherType voucherType, LocalDate date) {
        List<Voucher> vouchers = voucherService.getVoucherByTypeAndDate(voucherType, date);
        List<Customer> customers = new ArrayList<>();
        vouchers.forEach(voucher -> customerRepository.findByVoucherId(voucher.getVoucherId())
                .ifPresent(customers::add));
        return customers;
    }

    @Transactional
    public void update(UUID customerId, String name, String email, CustomerType customerType) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerDataException(NOT_SAVED));
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(customerId, name, email, customerType, now, now);
        customerRepository.update(customer);
        logger.info("Update customer, customer id : {}", customerId);
    }


    @Transactional
    public void remove(UUID customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerDataException(NOT_SAVED));
        customerRepository.deleteById(customerId);
        logger.info("Delete customer, customer id: {}", customerId);
    }
}

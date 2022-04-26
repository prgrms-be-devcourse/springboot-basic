package org.prgrms.kdt.domain.customer.service;

import org.prgrms.kdt.domain.customer.dto.CustomerCreateRequest;
import org.prgrms.kdt.domain.customer.dto.CustomerUpdateRequest;
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

import static org.prgrms.kdt.domain.common.exception.ExceptionType.NOT_SAVED;

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
        UUID customerId = customerRepository.save(customer);
        logger.info("create Customer Id: {}", customer.getCustomerId());
        return customerId;
    }

    public List<Customer> getBlackCustomers() {
        List<Customer> blackCustomers = customerRepository.findByType(CustomerType.BLACK_LIST);
        logger.info("Get blackList customers size: {}", blackCustomers.size());
        return blackCustomers;
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        logger.info("find Customer By Id {}", customerId);
        return customer;
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        logger.info("find Customer By email {}", email);
        return customer;
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
    public void update(Customer customer) {
        customerRepository.findById(customer.getCustomerId())
                .orElseThrow(() -> new CustomerDataException(NOT_SAVED));
        customerRepository.update(customer);
    }


    @Transactional
    public void remove(UUID customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerDataException(NOT_SAVED));
        customerRepository.deleteById(customerId);
        logger.info("Delete Customer id: {}", customerId);
    }
}

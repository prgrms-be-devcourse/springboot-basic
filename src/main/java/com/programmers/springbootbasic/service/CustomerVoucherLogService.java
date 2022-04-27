package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.repository.CustomerJdbcRepository;
import com.programmers.springbootbasic.repository.CustomerVoucherLogJdbcRepository;
import com.programmers.springbootbasic.repository.VoucherJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.springbootbasic.service.CustomerService.validateCustomerId;
import static com.programmers.springbootbasic.validation.CustomerValidator.NON_EXISTING_CUSTOMER_ID_EXCEPTION_MESSAGE;
import static com.programmers.springbootbasic.validation.VoucherValidator.DUPLICATE_VOUCHER_ID_EXCEPTION_MESSAGE;
import static com.programmers.springbootbasic.validation.VoucherValidator.NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE;

@Service
public class CustomerVoucherLogService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerVoucherLogService.class);

    private final CustomerVoucherLogJdbcRepository customerVoucherLogJdbcRepository;
    private final CustomerJdbcRepository customerJdbcRepository;
    private final VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    public CustomerVoucherLogService(CustomerVoucherLogJdbcRepository customerVoucherLogJdbcRepository, CustomerJdbcRepository customerJdbcRepository, VoucherJdbcRepository voucherJdbcRepository) {
        this.customerVoucherLogJdbcRepository = customerVoucherLogJdbcRepository;
        this.customerJdbcRepository = customerJdbcRepository;
        this.voucherJdbcRepository = voucherJdbcRepository;
    }

    public CustomerVoucherLog createCustomerVoucherLog(String customerId, UUID voucherId) {
        validateCustomerId(customerId);

        if(getCustomerById(customerId).isEmpty()) {
            throw new IllegalArgumentException(NON_EXISTING_CUSTOMER_ID_EXCEPTION_MESSAGE);
        }
        if (getVoucherById(voucherId).isEmpty()) {
            throw new IllegalArgumentException(NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE);
        }
        if(getCustomerVoucherLogByVoucherId(voucherId).isPresent()) {
            logger.info("이미 할당된 할인권을 재할당하려고 시도");
            throw new IllegalArgumentException(DUPLICATE_VOUCHER_ID_EXCEPTION_MESSAGE);
        }

        return customerVoucherLogJdbcRepository.insert(customerId, voucherId);
    }

    public List<CustomerVoucherLog> getAllCustomerVoucherLog() {
        return customerVoucherLogJdbcRepository.findAll();
    }

    public List<CustomerVoucherLog> getCustomerVoucherLogByCustomerId(String customerId) {
        return customerVoucherLogJdbcRepository.findByCustomerId(customerId);
    }

    public Optional<CustomerVoucherLog> getCustomerVoucherLogByVoucherId(UUID voucherId) {
        return customerVoucherLogJdbcRepository.findByVoucherId(voucherId);
    }

    public Optional<Customer> getCustomerHoldingVoucher(UUID voucherId) {
        return customerVoucherLogJdbcRepository.findCustomerByVoucherId(voucherId);
    }

    public List<Voucher> getVouchersOfCustomer(String customerId) {
        return customerVoucherLogJdbcRepository.findVouchersByCustomerId(customerId);
    }

    public Optional<Customer> getCustomerById(String customerId) {
        return customerJdbcRepository.findById(customerId);
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherJdbcRepository.findById(voucherId);
    }

}

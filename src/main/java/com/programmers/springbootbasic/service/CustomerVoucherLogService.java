package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.repository.StatusJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatusService {

    private final StatusJdbcRepository statusJdbcRepository;

    @Autowired
    public StatusService(StatusJdbcRepository statusJdbcRepository) {
        this.statusJdbcRepository = statusJdbcRepository;
    }

    public CustomerVoucherLog createStatus(String customerId, UUID voucherId) {
        return statusJdbcRepository.insert(customerId, voucherId);
    }

    public List<CustomerVoucherLog> getAllStatus() {
        return statusJdbcRepository.findAll();
    }

    public List<CustomerVoucherLog> getStatusByCustomerId(String customerId) {
        return statusJdbcRepository.findByCustomerId(customerId);
    }

    public Optional<CustomerVoucherLog> getStatusByVoucherId(UUID voucherId) {
        return statusJdbcRepository.findByVoucherId(voucherId);
    }

    public Optional<Customer> getCustomerHoldingVoucher(UUID voucherId) {
        return statusJdbcRepository.findCustomerByVoucherId(voucherId);
    }

    public List<Voucher> getVouchersOfCustomer(String customerId) {
        return statusJdbcRepository.findVouchersByCustomerId(customerId);
    }

}

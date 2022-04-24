package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.dto.CustomerDTO;
import com.programmers.springbootbasic.dto.StatusDTO;
import com.programmers.springbootbasic.dto.VoucherDTO;
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

    public StatusDTO createStatus(String customerId, UUID voucherId) {
        return statusJdbcRepository.insert(customerId, voucherId);
    }

    public List<StatusDTO> findAllStatus() {
        return statusJdbcRepository.findAll();
    }

    public List<StatusDTO> findStatusByCustomerId(String customerId) {
        return statusJdbcRepository.findByCustomerId(customerId);
    }

    public Optional<StatusDTO> findStatusByVoucherId(UUID voucherId) {
        return statusJdbcRepository.findByVoucherId(voucherId);
    }

    public Optional<CustomerDTO> findCustomerHoldingVoucher(UUID voucherId) {
        return statusJdbcRepository.findCustomerByVoucherId(voucherId);
    }

    public List<VoucherDTO> findVouchersOfCustomer(String customerId) {
        return statusJdbcRepository.findVouchersByCustomerId(customerId);
    }

}

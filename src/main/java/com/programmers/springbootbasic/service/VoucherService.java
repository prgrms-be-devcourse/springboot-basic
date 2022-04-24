package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.VoucherType;
import com.programmers.springbootbasic.dto.VoucherDTO;
import com.programmers.springbootbasic.repository.VoucherJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    public VoucherService(VoucherJdbcRepository voucherJdbcRepository) {
        this.voucherJdbcRepository = voucherJdbcRepository;
    }

    public VoucherDTO createVoucher(int type) {
        VoucherDTO newVoucher = VoucherType.createVoucherDTO(type);
        return voucherJdbcRepository.insert(newVoucher);
    }

    public Optional<VoucherDTO> findVoucherById(UUID voucherId) {
        return voucherJdbcRepository.findById(voucherId);
    }

    public List<VoucherDTO> findAvailableVouchers() {
        return voucherJdbcRepository.findAvailableVouchers();
    }

    public List<VoucherDTO> findAllVouchers() {
        return voucherJdbcRepository.findAll();
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherJdbcRepository.deleteById(voucherId);
    }

    public void deleteAllVouchers() {
        voucherJdbcRepository.deleteAll();
    }

}

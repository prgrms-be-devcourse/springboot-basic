package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.controller.dto.Benefit;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.domain.VoucherType;
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

    public Voucher createVoucher(int type) {
        Voucher newVoucher = VoucherType.createVoucher(type);

        return voucherJdbcRepository.insert(newVoucher);
    }

    public Voucher createVoucher(Benefit benefit) {
        Voucher newVoucher = VoucherType.createVoucher(benefit);

        return voucherJdbcRepository.insert(newVoucher);
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherJdbcRepository.findById(voucherId);
    }

    public List<Voucher> getAvailableVouchers() {
        return voucherJdbcRepository.findAvailableVouchers();
    }

    public List<Voucher> getAllVouchers() {
        return voucherJdbcRepository.findAll();
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherJdbcRepository.deleteById(voucherId);
    }

    public void deleteAllVouchers() {
        voucherJdbcRepository.deleteAll();
    }

}

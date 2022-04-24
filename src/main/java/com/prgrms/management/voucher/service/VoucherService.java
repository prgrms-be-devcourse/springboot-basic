package com.prgrms.management.voucher.service;

import com.prgrms.management.config.ErrorMessageType;
import com.prgrms.management.config.exception.NotFoundException;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        Voucher voucher = voucherRequest.of();
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public List<Voucher> findAllByVoucherTypeOrCreatedAt(VoucherType voucherType, LocalDate date) {
        return voucherRepository.findAllByVoucherTypeOrCreatedAt(voucherType, date);
    }

    public List<UUID> findCustomersByVoucherType(VoucherType voucherType) {
        return voucherRepository.findCustomerIdByVoucherType(voucherType);
    }

    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> new NotFoundException(this.getClass() + ErrorMessageType.NOT_EXIST_EXCEPTION.getMessage()));
    }

    public void updateVoucherByCustomerId(UUID voucherId, UUID customerId) {
        voucherRepository.updateVoucherByCustomerId(voucherId, customerId);
    }

    public void deleteVoucherByCustomerId(UUID customerId) {
        voucherRepository.deleteById(customerId);
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}

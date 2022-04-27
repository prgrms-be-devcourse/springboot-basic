package com.prgrms.management.voucher.service;

import com.prgrms.management.config.exception.NotExistException;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.dto.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.prgrms.management.config.ErrorMessageType.NOT_EXIST_EXCEPTION;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        return voucherRepository.save(voucherRequest.create());
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public List<Voucher> findAllByVoucherTypeOrCreatedAt(VoucherType voucherType, LocalDate date) {
        return voucherRepository.findAllByVoucherTypeOrCreatedAt(voucherType, date);
    }

    public List<UUID> findCustomersByVoucherType(VoucherType voucherType) {
        return voucherRepository.findCustomerByVoucherType(voucherType);
    }

    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> new NotExistException(this.getClass() + NOT_EXIST_EXCEPTION.getMessage()));
    }

    public void updateByCustomerId(UUID voucherId, UUID customerId) {
        voucherRepository.updateByCustomerId(voucherId, customerId);
    }

    public void deleteVoucherByCustomerId(UUID customerId) {
        voucherRepository.deleteById(customerId);
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}

package com.tangerine.voucher_system.application.voucher.service;

import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher findVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public Voucher findVoucherByVoucherType(VoucherType voucherType) {
        return voucherRepository.findByVoucherType(voucherType)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public Voucher findVoucherByCreatedAt(LocalDate createdAt) {
        return voucherRepository.findByCreatedAt(createdAt)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public Voucher deleteVoucherById(UUID voucherId) {
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        voucherRepository.deleteById(voucherId);
        return foundVoucher.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

}
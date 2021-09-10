package org.prgrms.kdt.service;

import java.time.LocalDateTime;
import org.prgrms.kdt.model.*;
import org.prgrms.kdt.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;


@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
            .findById(voucherId)
            .orElseThrow(
                () -> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAllVoucher();
    }

    @Transactional
    public Voucher createVoucher(VoucherType voucherType, Long discount) {
        var voucher = voucherRepository.insert(
            new Voucher(
                UUID.randomUUID(),
                discount,
                LocalDateTime.now(),
                voucherType,
                voucherType.getDiscountStrategy())
        );
        return getVoucher(voucher.getVoucherId());
    }

    @Transactional
    public Voucher updateVoucherType(Voucher voucher, VoucherType voucherType) {
        voucher.changeVoucherType(voucherType);
        voucherRepository.updateType(voucher);
        return getVoucher(voucher.getVoucherId());
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }

}

package com.prgrms.w3springboot.voucher.service;

import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherFactory;
import com.prgrms.w3springboot.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public UUID createVoucher(String voucherType, long discountAmount) {
        // voucherType, discountAmount -> domain
        Voucher voucher = VoucherFactory.createVoucher(voucherType, discountAmount);
        Voucher insertedVoucher = voucherRepository.insert(voucher);
        return insertedVoucher.getVoucherId();
    }

    // 나중에 구현
    public void useVoucher(Voucher voucher) {

    }
}

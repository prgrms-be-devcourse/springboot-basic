package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.storage.VoucherStorage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherCreator {

    private final VoucherStorage voucherStorage;

    public VoucherCreator(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public Voucher createVoucher(VoucherPolicy voucherPolicy, long amount) {
        Voucher voucher = null;
        switch (voucherPolicy) {
            case FIXED_VOUCHER:
                voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
                break;
            case PERCENT_VOUCHER:
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
        }
        if (voucher != null) {
            voucherStorage.save(voucher);
        }
        return voucher;
    }
}

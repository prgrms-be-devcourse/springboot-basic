package com.prgms.VoucherApp.model;

import com.prgms.VoucherApp.domain.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.domain.VoucherType;
import com.prgms.VoucherApp.storage.VoucherStorage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherCreator {

    private final VoucherStorage voucherStorage;

    public VoucherCreator(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public Voucher createVoucher(VoucherType voucherType, long amount) {
        Voucher voucher = switch (voucherType) {
            case FIXED_VOUCHER -> new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PERCENT_VOUCHER -> new PercentDiscountVoucher(UUID.randomUUID(), amount);
        };
        voucherStorage.save(voucher);
        return voucher;
    }
}

package com.prgms.VoucherApp.domain.voucher.model;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.storage.VoucherStorage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class VoucherCreator {

    private final VoucherStorage voucherStorage;

    public VoucherCreator(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public Voucher createVoucher(VoucherType voucherType, long amount) {
        Voucher voucher = switch (voucherType) {
            case FIXED_VOUCHER -> new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(amount), voucherType);
            case PERCENT_VOUCHER ->
                    new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(amount), voucherType);
        };
        voucherStorage.save(voucher);
        return voucher;
    }
}

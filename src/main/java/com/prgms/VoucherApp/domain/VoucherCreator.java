package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.storage.VoucherStorage;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherCreator {

    private final VoucherStorage voucherStorage;

    public VoucherCreator(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public Optional<Voucher> createVoucher(VoucherPolicy voucherPolicy, long amount) {
        Optional<Voucher> voucher = Optional.empty();
        switch (voucherPolicy) {
            case FIXED_VOUCHER:
                voucher = Optional.of(new FixedAmountVoucher(UUID.randomUUID(), amount));
                break;
            case PERCENT_VOUCHER:
                voucher = Optional.of(new PercentDiscountVoucher(UUID.randomUUID(), amount));
        }

        voucher.ifPresent(voucherStorage::save);
        return voucher;
    }
}

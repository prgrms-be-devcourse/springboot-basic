package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.InvalidITypeInputException;
import org.prgrms.kdt.storage.VoucherStorage;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.io.IOManager.getSelectWrongMessage;

@Service
public class VoucherProvider {

    private final VoucherStorage voucherStorage;

    public VoucherProvider(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public void create(VoucherType voucherType, double amount) {
        switch (voucherType) {
            case FIXED_VOUCHER -> {
                voucherStorage.save(new FixedAmountVoucher(UUID.randomUUID(), amount));
            }
            case PERCENT_VOUCHER -> {
                voucherStorage.save(new PercentDiscountVoucher(UUID.randomUUID(), amount));
            }
            default -> {
                throw new InvalidITypeInputException(getSelectWrongMessage());
            }
        }
    }

    public static Voucher getVoucher(VoucherType voucherType, UUID voucherId, double amount) {
        switch (voucherType) {
            case FIXED_VOUCHER -> {
                return new FixedAmountVoucher(voucherId, amount);
            }
            case PERCENT_VOUCHER -> {
                return new PercentDiscountVoucher(voucherId, amount);
            }
            default -> {
                throw new InvalidITypeInputException(getSelectWrongMessage());
            }
        }
    }

    public List<Voucher> list() {
        if (voucherStorage.findAll().isEmpty()) {
            return new ArrayList<>();
        }
        return voucherStorage.findAll();
    }
}

package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class VoucherFactory {

    private Voucher createFixedAmountVoucher(String discountValue) {
        return new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(discountValue));
    }

    private Voucher createPercentDiscountVoucher(String discountValue) {
        return new PercentDiscountVoucher(UUID.randomUUID(), Double.parseDouble(discountValue));
    }

    private Voucher createFixedAmountVoucher(String discountValue, String voucherId) {
        return new FixedAmountVoucher(UUID.fromString(voucherId), Long.parseLong(discountValue));
    }

    private Voucher createPercentDiscountVoucher(String discountValue, String voucherId) {
        return new PercentDiscountVoucher(UUID.fromString(voucherId), Double.parseDouble(discountValue));
    }

    public Voucher createVoucher(String voucherType, String discountValue) {
        String classNameString = VoucherStatus.getStringClassName(voucherType);

        if (classNameString.equals("FixedAmountVoucher")) {
            return createFixedAmountVoucher(discountValue);
        } else if (classNameString.equals("PercentDiscountVoucher")) {
            return createPercentDiscountVoucher(discountValue);
        } else {
            throw new NoSuchElementException("존재하지 않는 Voucher 형식입니다.");
        }
    }

    public Voucher createVoucher(String voucherType, String voucherId, String discountValue) {
        if (voucherType.equals("FixedAmountVoucher")) {
            return createFixedAmountVoucher(discountValue, voucherId);
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            return createPercentDiscountVoucher(discountValue, voucherId);
        } else {
            throw new NoSuchElementException("존재하지 않는 Voucher 형식입니다.");
        }
    }

    public String createVoucherClassNameString(Voucher voucher) {
        if (FixedAmountVoucher.class == voucher.getClass()) {
            return "FixedAmountVoucher";
        } else if (PercentDiscountVoucher.class == voucher.getClass()) {
            return "PercentDiscountVoucher";
        } else {
            throw new NoSuchElementException("존재하지 않는 Voucher 형식입니다.");
        }
    }
}

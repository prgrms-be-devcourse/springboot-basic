package org.prgrms.application.domain.voucher.typepolicy;

import org.prgrms.application.domain.voucher.VoucherType;

public interface VoucherTypePolicy {

    void validatePositive(double discountAmount);

    VoucherType getVoucherType();

    double getDiscountAmount();

    void changeDiscountAmount(double changeAmount);

    double discount(double beforeDiscount);
}

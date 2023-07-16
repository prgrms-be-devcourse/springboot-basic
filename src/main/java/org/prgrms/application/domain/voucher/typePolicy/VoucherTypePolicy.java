package org.prgrms.application.domain.voucher.typePolicy;

import org.prgrms.application.domain.voucher.VoucherType;

public interface VoucherTypePolicy {

    void validatePositive(double discountAmount);

    VoucherType getVoucherType();

    double discount(double beforeDiscount);
}

package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.model.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {

    public Voucher create(VoucherForm voucherForm) {

        VoucherType type = VoucherType.getType(voucherForm.getVoucherType());
        long value = voucherForm.getValue();

        UUID voucherId = UUID.randomUUID();
        switch (type) {
            case FIXEDAMOUNT:
                return new FixedAmountVoucher(voucherId, value);
            case PERCENTDISCOUNT:
                return new PercentDiscountVoucher(voucherId, value);
            default:
                throw new IllegalArgumentException("Voucher type error");
        }
    }

}

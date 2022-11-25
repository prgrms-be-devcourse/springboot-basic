package org.prgrms.voucherDTO;

import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.voucherType.VoucherType;

public class VoucherDTO {
    private final VoucherType type;
    private final Amount amount;

    public VoucherDTO(String type, long amount) {
        VoucherType voucherType = VoucherType.of(type);
        this.type = voucherType;
        this.amount = voucherType.generateAmount(amount);
    }

    public VoucherType getType() {
        return type;
    }

    public Amount getAmount() {
        return amount;
    }
}

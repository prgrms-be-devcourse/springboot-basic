package org.prgrms.application.domain.voucher;

import org.prgrms.application.domain.voucher.typePolicy.VoucherTypePolicy;
import org.prgrms.application.entity.VoucherEntity;

public class Voucher {
    private final Long voucherId;
    private final VoucherType voucherType;
    private final VoucherTypePolicy voucherTypePolicy;
    private final double discountAmount;

    public Voucher(Long voucherId, VoucherType voucherType, double discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.voucherTypePolicy = voucherType.applyPolicy(discountAmount);
        this.discountAmount = discountAmount;
    }

    public static Voucher of(Long voucherId, VoucherType voucherType, double discountAmount) {
        return new Voucher(voucherId, voucherType, discountAmount);
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public VoucherEntity toEntity(){
        return new VoucherEntity(this.voucherId, this.voucherType.name(), this.discountAmount);
    }
}

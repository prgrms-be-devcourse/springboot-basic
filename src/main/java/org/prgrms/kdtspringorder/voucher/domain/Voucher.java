package org.prgrms.kdtspringorder.voucher.domain;

import java.util.UUID;

import org.prgrms.kdtspringorder.voucher.enums.VoucherPolicy;
import org.prgrms.kdtspringorder.voucher.exception.ValidationException;


public class Voucher {
    private final VoucherPolicy voucherPolicy;
    private UUID voucherId;

    public Voucher(VoucherPolicy voucherPolicy) {
        this.voucherPolicy = voucherPolicy;
    }

    public void assignId(UUID id) {
        if (this.voucherId != null) {
            throw new ValidationException("이미 ID가 할당된 Voucher입니다.");
        }
        this.voucherId = id;
    }

    public UUID getId() {
        return this.voucherId;
    }

    public long discount(long originalPrice) {
        return this.voucherPolicy.discount(originalPrice);
    }

    public String getVoucherTypeInString() {
        return this.voucherPolicy.getPolicyType();
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + this.voucherId +
                ", voucherPolicy=" + this.voucherPolicy.getPolicyType() +
                '}';
    }
}

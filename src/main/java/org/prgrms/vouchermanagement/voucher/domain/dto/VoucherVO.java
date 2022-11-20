package org.prgrms.vouchermanagement.voucher.domain.dto;

import java.util.UUID;

public class VoucherVO {
    private final UUID voucherId;
    private final String voucherType;
    private final int discountAmount;
    private final UUID customerId;

    private VoucherVO(UUID voucherId, String voucherType, int discountAmount, UUID customerId) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
        this.customerId = customerId;
    }

    public static VoucherVO of(UUID voucherId, String voucherType, int discountAmount, UUID customerId) {
        return new VoucherVO(voucherId, voucherType, discountAmount, customerId);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}

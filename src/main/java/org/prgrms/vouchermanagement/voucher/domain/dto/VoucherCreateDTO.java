package org.prgrms.vouchermanagement.voucher.domain.dto;

import java.util.UUID;

public class VoucherCreateDTO {
    private final UUID voucherId;
    private final String voucherType;
    private final int discountAmount;
    private final UUID customerId;

    private VoucherCreateDTO(UUID voucherId, String voucherType, int discountAmount, UUID customerId) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
        this.customerId = customerId;
    }

    public static VoucherCreateDTO of(UUID voucherId, String voucherType, int discountAmount, UUID customerId) {
        return new VoucherCreateDTO(voucherId, voucherType, discountAmount, customerId);
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

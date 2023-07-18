package org.prgrms.kdt.entity;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.utils.VoucherStatus;


public class VoucherEntity {

    private static Long voucherId;
    private static String voucherType;
    private static Long amount;
    private static String status;

    public VoucherEntity() {
    }

    public VoucherEntity(Long voucherId, String voucherType, Long amount, String status) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
        this.status = status;
    }

    public static VoucherEntity from(Voucher voucher) {
        VoucherEntity voucherEntity = new VoucherEntity();
        voucherEntity.voucherId = voucher.getVoucherId();
        voucherEntity.voucherType = String.valueOf(voucher.getVoucherType());
        voucherEntity.amount = voucher.getDiscountAmount();
        voucherEntity.status = voucher.getStatus();

        return voucherEntity;
    }

    public Long getVoucherEntityId() {
        return voucherId;
    }

    public String getVoucherEntityType() {
        return voucherType;
    }

    public Long getEntityAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}

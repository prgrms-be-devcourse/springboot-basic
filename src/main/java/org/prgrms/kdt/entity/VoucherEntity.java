package org.prgrms.kdt.entity;

import org.prgrms.kdt.domain.voucher.Voucher;


public class VoucherEntity {

    private Long voucherId;
    private String voucherType;
    private Long amount;
    private boolean status;

    public VoucherEntity() {

    }

    public VoucherEntity(Long voucherId, String voucherType, Long amount, boolean status) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount= amount;
        this.status = status;
    }

    public VoucherEntity from(Voucher voucher) {
        voucherId = voucher.getVoucherId();
        voucherType = String.valueOf(voucher.getVoucherType());
        amount = voucher.getDiscountAmount();
        status = voucher.getStatus();

        return new VoucherEntity(voucherId, voucherType, amount, status);
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

    public boolean isEntityStatus() {
        return status;
    }
}

package org.prgrms.kdt.entity;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class VoucherEntity {

    private Long voucherEntityId;
    private String voucherEntityType;
    private Long entityAmount;
    private boolean entityStatus;

    public VoucherEntity() {

    }

    public VoucherEntity(Long voucherId, String voucherType, Long amount, boolean status) {
        voucherEntityId = voucherId;
        voucherEntityType = voucherType;
        entityAmount= amount;
        entityStatus = status;
    }

    public VoucherEntity toEntity(Voucher voucher) {
        voucherEntityId = voucher.getVoucherId();
        voucherEntityType = String.valueOf(voucher.getVoucherType());
        entityAmount = voucher.getDiscountAmount();
        entityStatus = voucher.getStatus();

        return new VoucherEntity(voucherEntityId, voucherEntityType, entityAmount, entityStatus);
    }

    public Long getVoucherEntityId() {
        return voucherEntityId;
    }

    public String getVoucherEntityType() {
        return voucherEntityType;
    }

    public Long getEntityAmount() {
        return entityAmount;
    }

    public boolean isEntityStatus() {
        return entityStatus;
    }
}

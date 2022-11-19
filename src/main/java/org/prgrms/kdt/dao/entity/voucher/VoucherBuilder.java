package org.prgrms.kdt.dao.entity.voucher;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class VoucherBuilder {

    private UUID voucherId;
    private String discountAmount;
    private String voucherType;
    private UUID ownedCustomerId;
    private LocalDateTime createdAt;

    public VoucherBuilder create() {
        voucherId = UUID.randomUUID();
        ownedCustomerId = null;
        createdAt = LocalDateTime.now();
        return this;
    }

    public VoucherBuilder setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
        return this;
    }

    public VoucherBuilder setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public VoucherBuilder setVoucherType(String voucherType) {
        this.voucherType = voucherType;
        return this;
    }

    public VoucherBuilder setOwnedCustomerId(String ownedCustomerId) {
        if (ownedCustomerId != null) {
            this.ownedCustomerId = UUID.fromString(ownedCustomerId);
        }
        return this;
    }

    public VoucherBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Voucher build() {
        if (Objects.equals(voucherType, VoucherType.FIXED_AMOUNT.getClassName())) {
            return new FixedAmountVoucher(voucherId, discountAmount, voucherType, ownedCustomerId, createdAt);
        }

        return new PercentDiscountVoucher(voucherId, discountAmount, voucherType, ownedCustomerId, createdAt);
    }
}

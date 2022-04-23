package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private UUID voucherId;
    private long discountAmount;
    private final LocalDateTime createAt;
    private Customer customer;
    private LocalDateTime ownedAt;
    private final int voucherType = VoucherType.FIXED_AMOUNT.getTypeNumber();
    private final static Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public FixedAmountVoucher(UUID voucherId, long discountAmount, LocalDateTime createAt) {
        validateDiscountAmount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.createAt = createAt;
    }

    public FixedAmountVoucher(UUID voucherId, long discountAmount, Customer customer, LocalDateTime ownedAt, LocalDateTime createAt) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.createAt = createAt;
        this.customer = customer;
        this.ownedAt = ownedAt;
    }


    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return this.discountAmount;
    }

    @Override
    public int getVoucherType() {
        return voucherType;
    }

    @Override
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public LocalDateTime getOwnedAt() {
        return ownedAt;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher { " +
                "voucherId = " + voucherId +
                ", discountAmount = " + discountAmount +
                " }";
    }

    private void validateDiscountAmount(long discountAmount) {
        if (discountAmount <= 0) {
            logger.info("input [discountAmount] -> {}", discountAmount);
            throw new IllegalArgumentException("discountAmount should be over 0");
        }
    }
}

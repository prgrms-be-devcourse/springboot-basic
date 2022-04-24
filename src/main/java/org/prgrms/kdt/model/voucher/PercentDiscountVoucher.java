package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountPercent;
    private final LocalDateTime createAt;
    private Customer customer;
    private LocalDateTime ownedAt;
    private final int voucherType = VoucherType.PERCENT_DISCOUNT.getTypeNumber();
    private static final int MAX_DISCOUNT_PERCENT = 100;

    private final static Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    public PercentDiscountVoucher(UUID voucherId, long discountPercent, LocalDateTime createAt) {
        validateDiscountPercent(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        this.createAt = createAt;
    }

    public PercentDiscountVoucher(UUID voucherId, long discountPercent, LocalDateTime createAt, Customer customer, LocalDateTime ownedAt) {
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        this.createAt = createAt;
        this.customer = customer;
        this.ownedAt = ownedAt;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long getDiscountAmount() {
        return discountPercent;
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
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", discountPercent=" + discountPercent +
                '}';
    }

    private void validateDiscountPercent(long discountPercent) {
        if (discountPercent <= 0) {
            logger.info("input [discountPercent] -> {}", discountPercent);
            throw new IllegalArgumentException("discountPercent should be over 0");
        }
        if (discountPercent > MAX_DISCOUNT_PERCENT) {
            logger.info("input [discountPercent] -> {}", discountPercent);
            throw new IllegalArgumentException("Max discountPercent is 100");
        }
    }
}

package org.prgrms.kdt.domain.voucher;

import lombok.Builder;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.error.ValidationException;

import static org.prgrms.kdt.error.Message.*;

@Builder
public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final Long voucherId;
    private final VoucherType type;
    private final long amount;
    private Long customerId;

    public FixedAmountVoucher(Long voucherId, VoucherType type, long amount, Long customerId) {
        validate(amount);
        this.voucherId = voucherId;
        this.type = type;
        this.amount = amount;
        this.customerId = customerId;
    }

    public FixedAmountVoucher(Long voucherId, VoucherType type, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.type = type;
        this.amount = amount;
    }

    public static FixedAmountVoucherBuilder builder() {
        return new FixedAmountVoucherBuilder();
    }

    private void validate(long amount) {
        if (amount < 0) throw new ValidationException(NEGATIVE_AMOUNT_MESSAGE);
        if (amount == 0) throw new ValidationException(ZERO_AMOUNT_MESSAGE);
        if (amount > MAX_VOUCHER_AMOUNT) throw new ValidationException(MAXIMUM_AMOUNT_MESSAGE);
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public void allocateCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", type=" + type +
                ", amount=" + amount +
                ", customerId=" + customerId +
                '}';
    }
}

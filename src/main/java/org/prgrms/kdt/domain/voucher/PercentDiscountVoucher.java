package org.prgrms.kdt.domain.voucher;

import lombok.Builder;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.exception.ValidationException;

import static org.prgrms.kdt.exception.Message.*;

@Builder
public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 100;

    private final Long voucherId;
    private final VoucherType type;
    private final long amount;
    private Long customerId;


    public PercentDiscountVoucher(Long voucherId, VoucherType type, long amount, Long customerId) {
        validate(amount);
        this.voucherId = voucherId;
        this.type = type;
        this.amount = amount;
        this.customerId = customerId;
    }

    public PercentDiscountVoucher(Long voucherId, VoucherType type, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.type = type;
        this.amount = amount;
    }

    public static PercentDiscountVoucherBuilder builder() {
        return new PercentDiscountVoucherBuilder();
    }

    private void validate(long percent) {
        if (percent < 0) throw new ValidationException(NEGATIVE_AMOUNT_MESSAGE);
        if (percent == 0) throw new ValidationException(ZERO_AMOUNT_MESSAGE);
        if (percent > MAX_VOUCHER_AMOUNT) throw new ValidationException(MAXIMUM_AMOUNT_MESSAGE);
    }


    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - amount / (double) 100));
    }

    @Override
    public Long getCustomerId() {
        return this.customerId;
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
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", type=" + type +
                ", percent=" + amount +
                ", customerId=" + customerId +
                '}';
    }
}

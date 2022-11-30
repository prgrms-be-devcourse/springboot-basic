package org.prgrms.voucher.voucherType;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.prgrms.exception.PaymentCannotBeNegativeException;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.discountType.DiscountAmount;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final Amount discountAmount;
    private final VoucherType type;
    private final LocalDateTime date;

    public FixedAmountVoucher(UUID voucherId, Amount discountAmount, LocalDateTime dateTime) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.type = VoucherType.FIXED;
        this.date = dateTime.withNano(0);
    }


    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - discountAmount.getValue();
        if (discountedAmount < 0) {
            throw new PaymentCannotBeNegativeException(discountedAmount);
        }
        return discountedAmount;
    }

    public VoucherType getVoucherType() {
        return type;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Amount getVoucherAmount() {
        return discountAmount;
    }

    @Override
    public Voucher changeAmountValue(long amount) {
        return new FixedAmountVoucher(this.voucherId, new DiscountAmount(amount), this.date);
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "*** FixedAmountVoucher ***"
            + System.lineSeparator()
            + "voucherId : " + voucherId
            + System.lineSeparator()
            + "discountAmount: " + discountAmount.getValue() + "won"
            + System.lineSeparator()
            + "createDate: " + getDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return Objects.equals(voucherId, that.voucherId) && Objects.equals(
            discountAmount, that.discountAmount) && type == that.type && Objects.equals(date,
            that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountAmount, type, date);
    }
}

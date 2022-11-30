package org.prgrms.voucher.voucherType;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.discountType.DiscountRate;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final Amount discountPercent;
    private final VoucherType type;
    private final LocalDateTime date;

    public PercentDiscountVoucher(UUID voucherId, Amount discountPercent, LocalDateTime dateTime) {
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        this.type = VoucherType.PERCENT;
        this.date = dateTime.withNano(0);
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - (discountPercent.getValue() / 100.0)));
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Amount getVoucherAmount() {
        return discountPercent;
    }

    @Override
    public VoucherType getVoucherType() {
        return type;
    }

    @Override
    public Voucher changeAmountValue(long amount) {
        return new PercentDiscountVoucher(this.voucherId, new DiscountRate(amount), this.date);
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "*** PercentDiscountVoucher ***"
            + System.lineSeparator()
            + "voucherId : " + voucherId
            + System.lineSeparator()
            + "discountRate: " + discountPercent.getValue() + "%"
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
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return Objects.equals(voucherId, that.voucherId) && Objects.equals(
            discountPercent, that.discountPercent) && type == that.type && Objects.equals(
            date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountPercent, type, date);
    }
}

package W3D2.jcu.voucher;

import java.util.UUID;
import lombok.Getter;

@Getter
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount)  {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher, " +
            "amount = " + amount;
    }
}

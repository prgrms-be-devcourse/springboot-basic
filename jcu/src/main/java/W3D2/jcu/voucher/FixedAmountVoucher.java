package W3D2.jcu.voucher;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

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

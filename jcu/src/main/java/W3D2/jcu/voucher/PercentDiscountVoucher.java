package W3D2.jcu.voucher;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher, " +
            "percent = " + percent;
    }
}

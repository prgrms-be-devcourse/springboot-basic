package W3D2.jcu.voucher.model;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher {
    private VoucherStatus voucherStatus = VoucherStatus.PERCENT;
    private final UUID voucherId;
    private final Long amount;

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * (100-amount / 100);
    }

    @Override
    public StringBuilder getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVoucherStatus()+" ");
        sb.append(getVoucherId()+" ");
        sb.append(getAmount()+" ");
        return sb;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher, " +
            "amount = " + amount;
    }
}

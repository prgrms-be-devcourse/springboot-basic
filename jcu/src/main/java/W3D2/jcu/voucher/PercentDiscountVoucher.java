package W3D2.jcu.voucher;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher{
    private VoucherStatus voucherStatus = VoucherStatus.PERCENT;
    private final UUID voucherId;
    private final Long percent;

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public StringBuilder getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVoucherStatus()+" ");
        sb.append(getVoucherId()+" ");
        sb.append(getPercent()+" ");
        return sb;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher, " +
            "percent = " + percent;
    }
}

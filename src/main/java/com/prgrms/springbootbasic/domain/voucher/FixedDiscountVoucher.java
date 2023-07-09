package com.prgrms.springbootbasic.domain.voucher;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class FixedDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;
    private final LocalDateTime createAt;
    private final VoucherType type;

    public FixedDiscountVoucher(long discount) {
        this.voucherId = UUID.randomUUID();
        this.discount = ValidDiscount(discount);
        this.createAt = getcreatedAt();
        this.type = getVoucherType();
    }


    public long ValidDiscount(long discount) {
        if (discount <= 0) {
            throw new IllegalArgumentException("고정 할인 바우처의 입력 금액은 0 이하를 입력할 수 없습니다.");
        }
        return discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public LocalDateTime getcreatedAt() {
        return createAt;
    }
}

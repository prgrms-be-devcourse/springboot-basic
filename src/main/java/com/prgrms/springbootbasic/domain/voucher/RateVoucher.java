package com.prgrms.springbootbasic.domain.voucher;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Setter;

@Setter
public class RateVoucher implements Voucher {

    private final UUID voucherId;
    private long discount;
    private final LocalDateTime createAt;
    private final VoucherType type;

    public RateVoucher(long discount) {
        this.voucherId = UUID.randomUUID();
        this.discount = ValidDiscount(discount);
        this.createAt = LocalDateTime.now();
        this.type = getVoucherType();
    }

    public long ValidDiscount(long discount) {
        if (discount < 1 || discount > 99) {
            throw new IllegalArgumentException("퍼센트 할인 바우처의 할인 퍼센트는 1 ~ 99까지의 숫자를 입력해야 합니다.");
        }
        return discount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.RATE;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createAt;
    }
}

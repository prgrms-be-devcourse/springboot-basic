package org.prgms.kdtspringweek1.voucher.entity;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountValue;
    private static final VoucherType voucherType = VoucherType.FIXED_AMOUNT;
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public static FixedAmountVoucher createWithAmount(long amount) {
        return new FixedAmountVoucher(amount);
    }

    public static FixedAmountVoucher createWithIdAndAmount(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    private FixedAmountVoucher(long discountValue) {
        // 해당 유효성 검사를 생성자가 아닌 다른 곳에서 구현한다면,
        // validate 호출 후 생성자를 다시 호출하게 되므로, 제 3자의 입장에서 유효성 검사를 빼먹을 수 있기에
        // 생성에 대한 유효성 검사를 생성 시에 같이 해주는 것이 좋다.
        if (discountValue > 0) {
            this.voucherId = UUID.randomUUID();
            this.discountValue = discountValue;
        } else {
            logger.debug("Fail to create {} -> {} amount", VoucherType.FIXED_AMOUNT.getName(), discountValue);
            throw new IllegalArgumentException(InputExceptionCode.INVALID_FIXED_AMOUNT.getMessage());
        }
    }

    private FixedAmountVoucher(UUID voucherId, long discountValue) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + discountValue +
                '}';
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscountValue() {
        return discountValue;
    }
}

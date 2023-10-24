package team.marco.vouchermanagementsystem.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static java.text.MessageFormat.format;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final int MINIMUM_AMOUNT = 1;

    private final int amount;

    public FixedAmountVoucher(int amount) {
        validate(amount);

        this.amount = amount;

        logger.debug("Create FixedAmountVoucher {id: {}, amount: {}}", id, amount);
    }

    private static void validate(int amount) {
        if (amount < MINIMUM_AMOUNT) {
            throw new IllegalArgumentException(
                    format("{0}: 할인 금액은 {1} 보다 작을 수 없습니다.", amount, MINIMUM_AMOUNT)
            );
        }
    }

    public FixedAmountVoucher(UUID id, int amount) {
        super(id);
        this.amount = amount;
    }

    @Override
    public String getInfo() {
        return format("{0}원 할인 쿠폰", amount);
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FIXED;
    }

    public int getAmount() {
        return amount;
    }
}

package team.marco.vouchermanagementsystem.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static team.marco.vouchermanagementsystem.model.voucher.VoucherType.FIXED;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final int MIN_AMOUNT = 100;
    private static final int MAX_AMOUNT = 1_000_000;

    private final VoucherType type = FIXED;
    private final int amount;

    public FixedAmountVoucher(int amount) {
        validate(amount);
        this.amount = amount;

        logger.debug("Create FixedAmountVoucher {id: {}, amount: {}}", getId(), amount);
    }

    public FixedAmountVoucher(UUID id, int amount) {
        super(id);
        validate(amount);
        this.amount = amount;

        logger.debug("Create FixedAmountVoucher {id: {}, amount: {}}", getId(), amount);
    }

    public FixedAmountVoucher(UUID id, int amount, UUID ownerId) {
        super(id, ownerId);
        validate(amount);
        this.amount = amount;

        logger.debug("Create FixedAmountVoucher {id: {}, amount: {}}", getId(), amount);
    }

    private void validate(int amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(format("{0}: 할인 금액은 {1}원 보다 작거나 {1}원 보다 클 수 없습니다.", amount, MIN_AMOUNT, MAX_AMOUNT));
        }
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}원 할인 쿠폰 {1}", amount, getOwnerId() == null ? "" : "/ 쿠폰 소지자: " + getOwnerId());
    }
}

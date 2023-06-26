package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public abstract class Voucher {
    protected static final Logger logger = LoggerFactory.getLogger(Voucher.class);
    private final VoucherType type;
    protected final UUID id;
    protected final long amount;

    public Voucher(VoucherType type, UUID id, long amount) {
        logger.info("{} 바우처 생성을 시작합니다...", type);

        checkValidAmount(type, amount);
        this.type = type;
        this.id = id;
        this.amount = amount;
    }

    public abstract long discount(long totalPrice);

    public UUID getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    private void checkValidAmount(VoucherType type, long amount) throws NoValidAmountException {
        switch (type) {
            case FIXED_AMOUNT -> checkValidFixedAmount(amount);
            case PERCENT_AMOUNT -> checkValidPercentAmount(amount);
        }
    }

    private void checkValidPercentAmount(long amount) {
        if (amount <= VoucherValue.ZERO || amount >= VoucherValue.ONE_HUNDRED) {
            logger.warn("사용자가 잘못된 값인 {}%를 입력했습니다.", amount);
            throw new NoValidAmountException(VoucherValue.NO_VALID_PERCENT_AMOUNT);
        }
    }

    private void checkValidFixedAmount(long amount) {
        if (amount <= VoucherValue.ZERO) {
            logger.warn("사용자가 잘못된 값인 {}원을 입력했습니다.", amount);
            throw new NoValidAmountException(VoucherValue.NO_VALID_FIXED_AMOUNT);
        }
    }
}

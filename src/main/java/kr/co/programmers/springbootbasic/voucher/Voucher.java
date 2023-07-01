package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private final VoucherType type;
    private final UUID id;
    private final long amount;
    private final LocalDateTime createdAt;
    protected static final long ZERO = 0;
    protected static final long ONE_HUNDRED = 100;
    protected static final long MAX_FIXED_AMOUNT = 1_000_000;
    protected static final Logger logger = LoggerFactory.getLogger(Voucher.class);

    public Voucher(VoucherType type, UUID id, long amount) {
        logger.info("{} 바우처 생성을 시작합니다...", type);

        checkValidAmount(type, amount);
        this.type = type;
        this.id = id;
        this.amount = amount;
        this.createdAt = null;
    }

    public Voucher(VoucherType type, UUID id, long amount, LocalDateTime createdAt) {
        logger.info("{} 바우처 생성을 시작합니다...", type);

        checkValidAmount(type, amount);
        this.type = type;
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public abstract long discount(long productPrice);

    public VoucherType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private void checkValidAmount(VoucherType type, long amount) throws NoValidAmountException {
        switch (type) {
            case FIXED_AMOUNT -> checkValidFixedAmount(amount);
            case PERCENT_AMOUNT -> checkValidPercentAmount(amount);
        }
    }

    private void checkValidPercentAmount(long amount) {
        if (amount <= ZERO || amount >= ONE_HUNDRED) {
            logger.warn("사용자가 잘못된 값인 {}%를 입력했습니다.", amount);
            throw new NoValidAmountException("고정 할인률 바우처의 생성 할인률이 잘못 됐습니다.\n\n");
        }
    }

    private void checkValidFixedAmount(long amount) {
        if (amount <= ZERO || amount > MAX_FIXED_AMOUNT) {
            logger.warn("사용자가 잘못된 값인 {}원을 입력했습니다.", amount);
            throw new NoValidAmountException("고정 금액 바우처의 생성 금액이 잘못 됐습니다.\n\n");
        }
    }
}

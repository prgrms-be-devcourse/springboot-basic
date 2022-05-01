package org.prgms.management.model.voucher;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final int MAX_AMOUNT = 100000;
    private static final int MIN_AMOUNT = 0;
    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 0;

    private final UUID voucherId;
    private int amount;
    private String name;
    private final String type;

    private final LocalDateTime createdAt;

    private FixedAmountVoucher(UUID voucherId, int amount, String name, String type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static FixedAmountVoucher getFixedAmountVoucher(
            UUID voucherId, int amount, String name,
            String type, LocalDateTime createdAt) {
        validateName(name);
        validateAmount(amount);

        return new FixedAmountVoucher(
                voucherId, amount, name, type, createdAt);
    }

    private static void validateName(String name) {
        if (name.length() == MIN_LENGTH) {
            throw new IllegalArgumentException(
                    MessageFormat.format("바우처 이름은 {0}글자를 넘어야 합니다.", MIN_LENGTH)
            );
        }

        if (name.length() == MAX_LENGTH) {
            throw new IllegalArgumentException(
                    MessageFormat.format("바우처 이름은 {0}글자 이상이면 안됩니다.", MAX_LENGTH)
            );
        }
    }

    private static void validateAmount(int amount) {
        if (amount <= MIN_AMOUNT) {
            throw new IllegalArgumentException(
                    MessageFormat.format("할인액은 {0}을 넘어야 합니다.", MIN_AMOUNT)
            );
        }

        if (amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(
                    MessageFormat.format("할인액은 {0} 이상이면 안됩니다.", MAX_AMOUNT)
            );
        }
    }

    @Override
    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    @Override
    public void changeDiscountNum(int discountNum) {
        validateAmount(discountNum);
        this.amount = discountNum;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getDiscountNum() {
        return this.amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}

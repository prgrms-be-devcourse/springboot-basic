package org.prgms.management.model.voucher;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private static final int MAX_PERCENT = 101;
    private static final int MIN_PERCENT = 0;
    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 0;

    private final UUID voucherId;
    private int percent;
    private String name;
    private final String type;

    private final LocalDateTime createdAt;

    private PercentAmountVoucher(UUID voucherId, int percent, String name, String type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static PercentAmountVoucher getPercentAmountVoucher(
            UUID voucherId, int percent, String name,
            String type, LocalDateTime createdAt) {
        validateName(name);
        validatePercent(percent);

        return new PercentAmountVoucher(
                voucherId, percent, name, type, createdAt);
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

    private static void validatePercent(int percent) {
        if (percent <= MIN_PERCENT) {
            throw new IllegalArgumentException(
                    MessageFormat.format("할인율은 {0}%을 넘어야 합니다.", MIN_PERCENT)
            );
        }

        if (percent > MAX_PERCENT) {
            throw new IllegalArgumentException(
                    MessageFormat.format("할인율은 {0}% 이상이면 안됩니다.", MAX_PERCENT)
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
        validatePercent(discountNum);
        this.percent = discountNum;
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
        return this.percent;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}

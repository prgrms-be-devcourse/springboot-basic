package org.devcourse.voucher.domain.voucher.amount;

import org.devcourse.voucher.domain.voucher.VoucherType;

public abstract class VoucherAmount {
    private final int amount;

    public VoucherAmount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    public static VoucherAmount of(VoucherType type, int amount) {
        validateType(type);
        return switch (type) {
            case PERCENT -> new PercentAmount(amount);
            case FIX -> new FixedAmount(amount);
            default -> throw new RuntimeException("사용 불가능한 타입입니다");
        };
    }

    public int getAmount() {
        return amount;
    }

    private void validate(int amount) {
        if (inValid(amount)) {
            throw new RuntimeException("바우처 금액 범위 오류");
        }
    }

    private static void validateType(VoucherType type) {
        if (type == null) {
            throw new RuntimeException("타입을 입력하세요");
        }
    }

    public abstract boolean inValid(int amount);
}

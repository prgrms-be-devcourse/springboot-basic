package team.marco.vouchermanagementsystem.model;

import static java.text.MessageFormat.format;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final int MAXIMUM_AMOUNT = (int) 1e9;
    private static final int MINIMUM_AMOUNT = 1;

    private final int amount;

    public FixedAmountVoucher(int amount) {
        validate(amount);

        this.amount = amount;
    }

    public FixedAmountVoucher(UUID id, int amount) {
        super(id);
        this.amount = amount;
    }

    private static void validate(int amount) {
        if (amount < MINIMUM_AMOUNT) {
            throw new IllegalArgumentException(
                    format("{0}: 할인 금액은 {1} 보다 작을 수 없습니다.", amount, MINIMUM_AMOUNT));
        }

        if (amount > MAXIMUM_AMOUNT) {
            throw new IllegalArgumentException(
                    format("{0}: 할인 금액은 {1} 보다 클 수 없습니다.", amount, MAXIMUM_AMOUNT));
        }
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FIXED;
    }

    @Override
    public int getData() {
        return amount;
    }

    @Override
    public String getInfo() {
        return format("{0}원 할인 쿠폰", amount);
    }
}

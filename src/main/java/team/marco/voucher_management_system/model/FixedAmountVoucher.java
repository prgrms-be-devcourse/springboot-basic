package team.marco.voucher_management_system.model;

import static java.text.MessageFormat.format;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import team.marco.voucher_management_system.type_enum.VoucherType;

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

    public FixedAmountVoucher(UUID id, int amount, LocalDateTime createAt) {
        super(id);

        this.amount = amount;
        this.createAt = createAt;
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
        return MessageFormat.format("id  : {0}\n설명 : {1}원 할인 쿠폰", id, amount);
    }
}

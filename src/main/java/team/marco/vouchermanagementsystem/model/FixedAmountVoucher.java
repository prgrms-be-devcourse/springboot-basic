package team.marco.vouchermanagementsystem.model;

import java.util.UUID;

import static java.text.MessageFormat.format;

public class FixedAmountVoucher implements Voucher {
    private final UUID id;
    private final int amount;

    public FixedAmountVoucher(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(format("{0}: 할인 금액은 0 보다 커야 합니다.", amount));
        }

        this.id = UUID.randomUUID();
        this.amount = amount;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getInfo() {
        return format("{0}원 할인 쿠폰", amount);
    }
}

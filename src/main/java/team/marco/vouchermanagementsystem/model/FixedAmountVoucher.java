package team.marco.vouchermanagementsystem.model;

import team.marco.vouchermanagementsystem.model.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID id;
    private final int amount;

    public FixedAmountVoucher(int amount) {
        this.id = UUID.randomUUID();
        this.amount = amount;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getInfo() {
        return MessageFormat.format("{0}원 할인 쿠폰", amount);
    }
}

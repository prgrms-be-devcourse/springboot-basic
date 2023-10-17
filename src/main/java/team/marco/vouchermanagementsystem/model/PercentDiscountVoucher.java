package team.marco.vouchermanagementsystem.model;

import team.marco.vouchermanagementsystem.model.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID id;
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        this.id = UUID.randomUUID();
        this.percent = percent;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getInfo() {
        return MessageFormat.format("{0}% 할인 쿠폰", percent);
    }
}

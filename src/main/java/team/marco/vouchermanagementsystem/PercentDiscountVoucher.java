package team.marco.vouchermanagementsystem;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID id;
    private final int amount;

    public PercentDiscountVoucher(int amount) {
        this.id = UUID.randomUUID();
        this.amount = amount;
    }
}

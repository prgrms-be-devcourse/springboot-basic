package team.marco.vouchermanagementsystem.model;

import team.marco.vouchermanagementsystem.model.Voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID id;
    private final int amount;

    public FixedAmountVoucher(int amount) {
        this.id = UUID.randomUUID();
        this.amount = amount;
    }
}

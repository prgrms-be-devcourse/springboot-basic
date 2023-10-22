package team.marco.vouchermanagementsystem.repository;

import team.marco.vouchermanagementsystem.model.voucher.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.voucher.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.voucher.Voucher;
import team.marco.vouchermanagementsystem.model.voucher.VoucherType;

import java.util.UUID;

public class LoadedJSONVoucher {
    private UUID id;
    private VoucherType type;
    private int amount;
    private int percent;

    private LoadedJSONVoucher() {
        // for object mapper deserializing
    }

    public Voucher convertToVoucher() {
        return switch (getType()) {
            case FIXED -> new FixedAmountVoucher(getId(), getAmount());
            case PERCENT -> new PercentDiscountVoucher(getId(), getPercent());
        };
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getPercent() {
        return percent;
    }
}

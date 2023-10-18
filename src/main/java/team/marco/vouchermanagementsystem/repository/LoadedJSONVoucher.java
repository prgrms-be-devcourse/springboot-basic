package team.marco.vouchermanagementsystem.repository;

import team.marco.vouchermanagementsystem.model.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;
import team.marco.vouchermanagementsystem.model.VoucherType;

import java.util.UUID;

public class LoadedJSONVoucher {
    private VoucherType type;
    private UUID id;
    private int data;
    private String info;

    private LoadedJSONVoucher() {
        // for object mapper deserializing
    }

    public Voucher convertToVoucher() {
        return switch (getType()) {
            case FIXED -> new FixedAmountVoucher(getId(), getData());
            case PERCENT -> new PercentDiscountVoucher(getId(), getData());
        };
    }

    public VoucherType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public int getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }
}

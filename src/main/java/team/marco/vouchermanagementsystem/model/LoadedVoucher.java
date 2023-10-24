package team.marco.vouchermanagementsystem.model;

import java.util.UUID;

public class LoadedVoucher {
    private UUID id;
    private VoucherType type;
    private String info;
    private int amount;
    private int percent;

    private LoadedVoucher() {
        // for object mapper deserializing
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public int getAmount() {
        return amount;
    }

    public int getPercent() {
        return percent;
    }
}

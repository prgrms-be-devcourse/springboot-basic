package team.marco.vouchermanagementsystem.model;

import java.util.UUID;

public class LoadedVoucher extends Voucher {
    private VoucherType type;
    private int data;

    private LoadedVoucher() {
        // for object mapper deserializing
    }

    public LoadedVoucher(UUID id, VoucherType type, int data) {
        super(id);
        this.type = type;
        this.data = data;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public int getData() {
        return data;
    }

    @Override
    public String getInfo() {
        return this.getClass().getName();
    }
}

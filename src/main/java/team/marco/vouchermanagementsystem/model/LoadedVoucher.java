package team.marco.vouchermanagementsystem.model;

public class LoadedVoucher extends Voucher {
    private VoucherType type;
    private int data;

    private LoadedVoucher() {
        // for object mapper deserializing
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

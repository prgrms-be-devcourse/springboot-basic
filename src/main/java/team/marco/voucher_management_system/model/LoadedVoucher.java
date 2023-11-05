package team.marco.voucher_management_system.model;

import java.time.LocalDateTime;
import java.util.UUID;
import team.marco.voucher_management_system.type_enum.VoucherType;

public class LoadedVoucher extends Voucher {
    private VoucherType type;
    private int data;

    private LoadedVoucher() {
        // for object mapper deserializing
    }

    public LoadedVoucher(UUID id, VoucherType type, int data, LocalDateTime createAt) {
        super(id);

        this.type = type;
        this.data = data;
        this.createAt = createAt;
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

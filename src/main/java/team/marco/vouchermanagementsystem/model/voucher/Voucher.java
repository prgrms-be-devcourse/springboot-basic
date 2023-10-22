package team.marco.vouchermanagementsystem.model.voucher;

import java.util.UUID;

public abstract class Voucher {
    private final UUID id;

    public Voucher() {
        this.id = UUID.randomUUID();
    }

    public Voucher(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    abstract public VoucherType getType();
}

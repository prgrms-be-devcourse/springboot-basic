package team.marco.vouchermanagementsystem.model;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID id;

    protected Voucher() {
        this.id = UUID.randomUUID();
    }

    protected Voucher(UUID id) {
        this.id = id;
    }

    public abstract String getInfo();

    public abstract VoucherType getType();

    public UUID getId() {
        return id;
    }
}

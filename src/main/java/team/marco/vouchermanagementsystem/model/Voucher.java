package team.marco.vouchermanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID id;

    protected Voucher() {
        this.id = UUID.randomUUID();
    }

    protected Voucher(UUID id) {
        this.id = id;
    }

    public final UUID getId() {
        return id;
    }

    public abstract VoucherType getType();

    public abstract int getData();

    @JsonIgnore
    public abstract String getInfo();
}

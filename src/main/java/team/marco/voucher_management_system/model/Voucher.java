package team.marco.voucher_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import team.marco.voucher_management_system.type_enum.VoucherType;

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

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", type=" + getType() +
                ", data=" + getData() +
                '}';
    }
}

package team.marco.voucher_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.UUID;
import team.marco.voucher_management_system.type_enum.VoucherType;

public abstract class Voucher {
    protected final UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime createAt;

    protected Voucher() {
        this.id = UUID.randomUUID();
        this.createAt = LocalDateTime.now();
    }

    protected Voucher(UUID id) {
        this.id = id;
        this.createAt = LocalDateTime.now();
    }

    public boolean isSameId(UUID id) {
        return this.id.equals(id);
    }

    public final UUID getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
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

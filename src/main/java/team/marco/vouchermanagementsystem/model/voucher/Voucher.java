package team.marco.vouchermanagementsystem.model.voucher;

import java.util.UUID;

public abstract class Voucher {
    private final UUID id;
    private UUID ownerId;

    public Voucher() { this.id = UUID.randomUUID(); }

    public Voucher(UUID id) {
        this.id = id;
    }

    public Voucher(UUID id, UUID owner) {
        this(id);
        this.ownerId = owner;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() { return ownerId; }

    abstract public VoucherType getType();

    public void assigneOwner(UUID owner) {
        this.ownerId = ownerId;
    }
}

package org.prgrms.springorder.domain.customer.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class BlockCustomer {

    private final UUID blockId;

    private final UUID customerId;

    private final LocalDateTime createdAt;

    public BlockCustomer(UUID blockId, UUID customerId, LocalDateTime createdAt) {
        this.blockId = blockId;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public UUID getBlockId() {
        return blockId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlockCustomer that = (BlockCustomer) o;
        return Objects.equals(blockId, that.blockId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId);
    }

    @Override
    public String toString() {
        return "BlockCustomer{" +
            "blockId=" + blockId +
            ", customerId=" + customerId +
            ", registeredAt=" + createdAt +
            '}';
    }
}

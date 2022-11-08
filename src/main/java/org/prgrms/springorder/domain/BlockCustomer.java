package org.prgrms.springorder.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class BlockCustomer {

    private UUID blockId;

    private UUID customerId;

    private LocalDateTime registeredAt;

    public BlockCustomer(UUID blockId, UUID customerId, LocalDateTime registeredAt) {
        this.blockId = blockId;
        this.customerId = customerId;
        this.registeredAt = registeredAt;
    }

    public UUID getBlockId() {
        return blockId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
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
}

package org.prgms.management.blacklist.entity;

import java.time.LocalDateTime;
import java.util.UUID;

// TODO : Customer 대신 customerId를 필드값으로 쓰는게 좋아보인다. 이유는 처음 저장공간에서 가져올 때 Customer의 정보를 가져와야 하는데, 컨트롤러단에서 customer 서비스를 호출하는게 나아보여서다.
// TODO : this를 쓰는 이유 찾아보기
public class Blacklist {
    private final UUID blacklistId;
    private final UUID customerId;

    private final LocalDateTime createdAt;

    public Blacklist(UUID blacklistId, UUID customerId, LocalDateTime createdAt) {
        this.blacklistId = blacklistId;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public UUID getBlacklistId() {
        return this.blacklistId;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}

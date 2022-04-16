package org.prgrms.kdt.domain.base;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}

package org.prgrms.kdt.domain.common.model;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public BaseEntity(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void changeModifiedDateTime() {
        this.modifiedDateTime = LocalDateTime.now();
    }
}

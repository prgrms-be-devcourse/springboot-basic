package org.prgrms.kdt.common;

import java.time.LocalDateTime;

public abstract class Date {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Date(LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
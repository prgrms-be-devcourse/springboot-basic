package com.programmers.springbootbasic.common;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class BaseEntity {

    private final LocalDateTime createdAt;

    protected BaseEntity(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity that)) {
            return false;
        }
        return Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedAt());
    }
}

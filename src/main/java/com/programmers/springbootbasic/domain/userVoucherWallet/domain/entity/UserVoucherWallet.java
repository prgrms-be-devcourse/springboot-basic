package com.programmers.springbootbasic.domain.userVoucherWallet.domain.entity;

import com.programmers.springbootbasic.common.BaseEntity;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

//TODO: 이름 변경
public class UserVoucherWallet extends BaseEntity {

    private final Long id;
    private final Long userId;
    private final UUID voucherId;

    public UserVoucherWallet(Long id, Long userId, UUID voucherId, LocalDateTime createdAt) {
        super(createdAt);
        this.id = id;
        this.userId = userId;
        this.voucherId = voucherId;
    }

    public UserVoucherWallet(Long userId, UUID voucherId, LocalDateTime createdAt) {
        this(null, userId, voucherId, createdAt);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserVoucherWallet that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUserId(),
            that.getUserId()) && Objects.equals(getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getVoucherId());
    }
}

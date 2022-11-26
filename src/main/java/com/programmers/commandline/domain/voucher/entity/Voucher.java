package com.programmers.commandline.domain.voucher.entity;

import com.programmers.commandline.domain.voucher.dto.VoucherInsetRequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private String id;
    private String type;
    private long discount;
    private String createdAt;

    protected Voucher(UUID uuid, VoucherType type, long discount, LocalDateTime createdAt) {
        this.id = uuid.toString();
        this.type = type.toString();
        this.discount = discount;
        this.createdAt = createdAt.toString();
    }

    protected Voucher(VoucherInsetRequestDto requestDto) {
        this.id = UUID.randomUUID().toString();
        this.type = VoucherType.valueOf(requestDto.type()).toString();
        this.discount = requestDto.discount();
        this.createdAt = LocalDateTime.now().toString();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public long getDiscount() {
        return discount;
    }

    public void update(long discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Type: %s Discount: %s CreatedAt: %s", this.id, this.type, this.discount, this.createdAt);
    }
}

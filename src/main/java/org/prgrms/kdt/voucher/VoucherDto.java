package org.prgrms.kdt.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:29 오전
 */
public class VoucherDto {

    private UUID voucherId;

    private String name;

    private Long discount;

    private String voucherType;

    private LocalDateTime createdAt;

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

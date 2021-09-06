package org.prgrms.kdt.voucher;

import java.time.LocalDateTime;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:29 오전
 */
public class VoucherDto {

    private String voucherId;

    private String name;

    private Long discount;

    private String voucherType;

    private String createdAt;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

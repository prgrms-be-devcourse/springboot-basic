package com.prgms.management.voucher.entity;

import com.prgms.management.voucher.exception.InvalidVoucherParameterException;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@ToString
public class PercentDiscountVoucher implements Voucher {
    private final static Integer MIN_PERCENT = 0;
    private final static Integer MAX_PERCENT = 100;
    private final Integer percent;
    private final Timestamp createdAt;
    private final VoucherType type = VoucherType.PERCENT;
    private UUID voucherId;
    private String name;

    public PercentDiscountVoucher(Integer percent) {
        this(UUID.randomUUID(), percent);
    }

    public PercentDiscountVoucher(UUID voucherId, Integer percent) {
        this(voucherId, "demo", percent, Timestamp.valueOf(LocalDateTime.now()));
    }

    public PercentDiscountVoucher(UUID voucherId, String name, Integer percent, Timestamp createdAt) {
        if (percent < MIN_PERCENT || percent > MAX_PERCENT) {
            throw new InvalidVoucherParameterException(MIN_PERCENT + "과 " + MAX_PERCENT + "사이의 값을 입력해주세요.");
        }
        this.voucherId = voucherId;
        this.name = name;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public int getVoucherFigure() {
        return percent;
    }

    @Override
    public String getVoucherType() {
        return type.toString();
    }

    @Override
    public void resetVoucherId() {
        voucherId = UUID.randomUUID();
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * ((100 - percent) / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return Objects.equals(percent, that.percent) && type == that.type && Objects.equals(voucherId, that.voucherId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(percent, type, voucherId, name);
    }

    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + voucherId + "," + percent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Timestamp getCreatedAt() {
        return createdAt;
    }
}

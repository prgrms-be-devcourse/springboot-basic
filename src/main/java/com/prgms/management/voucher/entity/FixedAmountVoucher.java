package com.prgms.management.voucher.entity;

import com.prgms.management.voucher.exception.InvalidVoucherParameterException;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@ToString
public class FixedAmountVoucher implements Voucher {
    private final static Integer MAX_AMOUNT = 10000;
    private final static Integer MIN_AMOUNT = 0;
    private final Integer amount;
    private final Timestamp createdAt;
    private final VoucherType type = VoucherType.FIXED;
    private UUID voucherId;
    private String name;

    public FixedAmountVoucher(Integer amount) {
        this(UUID.randomUUID(), amount);
    }

    public FixedAmountVoucher(UUID voucherId, Integer amount) {
        this(voucherId, "demo", amount, Timestamp.valueOf(LocalDateTime.now()));
    }

    public FixedAmountVoucher(UUID voucherId, String name, Integer amount, Timestamp createdAt) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new InvalidVoucherParameterException(MIN_AMOUNT + "과 " + MAX_AMOUNT + "사이의 값을 입력해주세요.");
        }
        this.voucherId = voucherId;
        this.name = name;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public int getVoucherFigure() {
        return amount;
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
        return beforeDiscount - amount;
    }

    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + voucherId + "," + amount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return Objects.equals(amount, that.amount) && type == that.type && Objects.equals(voucherId, that.voucherId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, type, voucherId, name);
    }
}

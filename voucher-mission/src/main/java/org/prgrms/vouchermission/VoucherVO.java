package org.prgrms.vouchermission;

import java.time.LocalDate;
import java.util.Objects;

public class VoucherVO {
    private final long value;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;

    public VoucherVO(long value, LocalDate createdDate, LocalDate expirationDate) {
        this.value = value;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    public long getValue() {
        return value;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherVO voucherVO = (VoucherVO) o;
        return value == voucherVO.value && createdDate.equals(voucherVO.createdDate) && expirationDate.equals(voucherVO.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, createdDate, expirationDate);
    }
}

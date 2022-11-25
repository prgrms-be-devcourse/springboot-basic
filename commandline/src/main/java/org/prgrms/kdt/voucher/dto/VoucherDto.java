package org.prgrms.kdt.voucher.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherDto {
    public long voucherId;
    public String typeNumber;
    public long discountDegree;

    public VoucherDto() {
    }

    public VoucherDto(long voucherId, String typeNumber, long discountDegree) {
        this.voucherId = voucherId;
        this.typeNumber = typeNumber;
        this.discountDegree = discountDegree;
    }

    @Override
    public String toString() {
        return "VoucherDto{" +
                "voucherId=" + voucherId +
                ", typeNumber='" + typeNumber + '\'' +
                ", discountDegree=" + discountDegree +
                '}';
    }
}

package org.prgrms.kdt.voucher.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherFormDto {
    public String typeNumber;
    public long discountDegree;

    public VoucherFormDto(){
    }
    public VoucherFormDto(String typeNumber, String discountDegree) {
        this.typeNumber = typeNumber;
        this.discountDegree = Long.parseLong(discountDegree);
    }

    @Override
    public String toString() {
        return "VoucherFormDto{" +
                "typeNumber='" + typeNumber + '\'' +
                ", discountDegree=" + discountDegree +
                '}';
    }
}

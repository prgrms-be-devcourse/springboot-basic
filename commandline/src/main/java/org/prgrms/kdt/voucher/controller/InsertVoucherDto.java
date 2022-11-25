package org.prgrms.kdt.voucher.controller;

import lombok.Data;

@Data
public class InsertVoucherDto {

    public String typeNumber;
    public long discountDegree;

    public InsertVoucherDto() {
    }

    public InsertVoucherDto(String typeNumber, long discountDegree) {
        this.typeNumber = typeNumber;
        this.discountDegree = discountDegree;
    }
}

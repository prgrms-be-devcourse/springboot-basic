package org.promgrammers.springbootbasic.dto.response;

import org.promgrammers.springbootbasic.domain.VoucherType;

import java.util.UUID;

public record VoucherResponse(UUID voucherId, VoucherType voucherType, long amount) {

    @Override
    public String toString() {
        return "생성된 바우처 : [ " +
                "voucherId : " + voucherId +
                ", voucherType : " + voucherType +
                ", amount : " + amount +
                " ]";
    }
}

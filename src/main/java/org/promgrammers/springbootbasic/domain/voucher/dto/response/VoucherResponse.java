package org.promgrammers.springbootbasic.domain.voucher.dto.response;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;

import java.util.UUID;

public record VoucherResponse(UUID voucherId, VoucherType voucherType, long amount) {

    public VoucherResponse(Voucher voucher) {
        this(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    public String voucherOutput() {
        return "    바우처 정보 : [ " +
                "voucherId : " + voucherId +
                ", voucherType : " + voucherType +
                ", amount : " + amount + " ]";
    }
}

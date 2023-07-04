package org.devcourse.voucher.controller.console.dto;

import org.devcourse.voucher.domain.voucher.vo.VoucherVO;

public record VoucherInfoResponse(long id, String voucherType, int amount) {

    public VoucherInfoResponse(VoucherVO value) {
        this(value.id(), value.voucherType(), value.amount());
    }
}

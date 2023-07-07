package org.devcourse.voucher.controller.console.dto;

import org.devcourse.voucher.domain.voucher.vo.VoucherVO;

public record VoucherInfoResponse(long id, String voucherType, int amount) {

    private static final String MESSAGE_TEMPLATE = "Voucher ID : %d, TYPE : %s, AMOUNT : %d";

    public VoucherInfoResponse(VoucherVO value) {
        this(value.id(), value.voucherType(), value.amount());
    }

    public String convertToMessage() {
        return MESSAGE_TEMPLATE.formatted(id, voucherType, amount);
    }
}

package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.domain.VoucherType;

public record VoucherDto(
        // TODO : 웹에서 입력시 예외처리 필요
        long value,
        VoucherType type
) {

}

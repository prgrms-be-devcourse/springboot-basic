package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.model.VoucherType;

import java.util.UUID;

public record VoucherDto(
        long value,
        VoucherType type
) {

}

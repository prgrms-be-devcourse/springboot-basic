package com.prgrms.springbasic.domain.voucher.dto;

import java.util.UUID;

public record UpdateVoucherRequest(
        UUID voucher_id, long discountValue
) {
}

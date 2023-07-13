package org.prgms.vouchermanagement.voucher.domain.dto.request;

import org.prgms.vouchermanagement.voucher.VoucherType;

public record VoucherSaveRequestDto (long discount, VoucherType voucherType) {
}

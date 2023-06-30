package org.promgrammers.springbootbasic.domain.voucher.dto.request;

import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;

import java.util.UUID;

public record UpdateVoucherRequest(UUID voucherId, VoucherType voucherType, long discountAmount) {

}

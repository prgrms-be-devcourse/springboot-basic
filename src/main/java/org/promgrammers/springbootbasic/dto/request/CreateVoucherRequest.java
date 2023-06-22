package org.promgrammers.springbootbasic.dto.request;

import org.promgrammers.springbootbasic.domain.VoucherType;

public record CreateVoucherRequest(VoucherType voucherType, long discountAmount) {

}

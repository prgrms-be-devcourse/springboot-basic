package org.prgrms.springbasic.controller.api.request;

import org.prgrms.springbasic.domain.voucher.VoucherType;

public record VoucherRequest(VoucherType voucherType, long discountInfo) {

}
package com.prgmrs.voucher.dto.request;

import com.prgmrs.voucher.enums.VoucherSelectionType;

public record VoucherRequest(VoucherSelectionType voucherSelectionType, String token) {

}
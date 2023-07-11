package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public record VoucherListResponse(List<Voucher> voucherList) {
}

package com.prgmrs.voucher.dto.request;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public record UserListRequest(String order, List<Voucher> voucherList) {
}

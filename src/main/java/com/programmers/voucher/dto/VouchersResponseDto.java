package com.programmers.voucher.dto;

import com.programmers.voucher.domain.Voucher;

import java.util.List;

public record VouchersResponseDto(List<Voucher> vouchers) {

}

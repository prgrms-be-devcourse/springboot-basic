package com.programmers.domain.voucher.dto;

import com.programmers.domain.voucher.Voucher;

import java.util.List;

public record VouchersResponseDto(List<Voucher> vouchers) {

}

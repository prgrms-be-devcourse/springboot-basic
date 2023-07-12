package com.programmers.springbootbasic.voucher.dto;

import com.programmers.springbootbasic.voucher.domain.Voucher;

import java.util.List;

public record VouchersResponseDto(List<Voucher> vouchers) {

}


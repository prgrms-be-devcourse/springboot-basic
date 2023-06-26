package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;

import java.util.List;

public interface VoucherService {

    Voucher create(VoucherRequestDto requestDto);

    List<Voucher> findVouchers();
}

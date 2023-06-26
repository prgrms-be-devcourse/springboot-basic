package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VoucherRequestDto;

import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher create(VoucherRequestDto requestDto);

    List<Voucher> findVouchers();

    Voucher findVoucher(UUID voucherId);
}

package com.programmers.voucher.service;

import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    UUID create(VoucherRequestDto voucherRequestDto);

    List<VoucherResponseDto> findVouchers();
}

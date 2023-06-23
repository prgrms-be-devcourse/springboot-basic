package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;

import java.util.List;
import java.util.UUID;

public interface VoucherController {

    UUID createVoucher(VoucherCreateRequest request);

    List<Voucher> findVouchers();
}

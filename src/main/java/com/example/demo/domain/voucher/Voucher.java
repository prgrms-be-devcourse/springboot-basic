package com.example.demo.domain.voucher;

import com.example.demo.dto.VoucherDto;

public interface Voucher {

    double disCount(int beforeAmount);
    VoucherDto convertToVoucherDto();
}

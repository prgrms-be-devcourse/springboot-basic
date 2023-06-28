package com.programmers.console.view;

import com.programmers.voucher.dto.VoucherResponseDto;

import java.util.List;

public interface OutputView {

    void printVouchers(List<VoucherResponseDto> vouchers);

    void printVoucher(VoucherResponseDto responseDto);

    <T> void println(T message);

    <T> void print(T message);
}

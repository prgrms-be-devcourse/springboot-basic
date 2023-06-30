package com.programmers.springmission.view;

import com.programmers.springmission.voucher.response.VoucherResponse;

import java.util.List;

public interface Output {
    void write(String message);

    void write(List<VoucherResponse> voucherResponses);
}


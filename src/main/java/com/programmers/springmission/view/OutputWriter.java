package com.programmers.springmission.view;

import com.programmers.springmission.voucher.presentation.response.VoucherResponse;

import java.util.List;

public class OutputWriter implements Output {

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void write(List<VoucherResponse> voucherResponses) {
        for (VoucherResponse voucherResponse : voucherResponses) {
            System.out.println(voucherResponse.toString());
        }
    }
}


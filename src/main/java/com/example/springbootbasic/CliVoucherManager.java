package com.example.springbootbasic;

import com.example.springbootbasic.io.Input;
import com.example.springbootbasic.io.Output;
import com.example.springbootbasic.voucher.VoucherRepository;

public class CliVoucherManager implements VoucherManager{
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;

    public CliVoucherManager(Input input, Output output, VoucherRepository voucherRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void run() {

    }
}

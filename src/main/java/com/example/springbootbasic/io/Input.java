package com.example.springbootbasic.io;

import com.example.springbootbasic.voucher.Voucher;

import java.util.Optional;

public interface Input {
    Optional<Command> getInputCommand(String prompt);
    Voucher inputVoucherInfo();
}

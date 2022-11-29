package com.programmers.assignment.voucher.engine.io;

public interface Input {
    String inputCommand(String commandMessage);

    String selectVoucher(String voucher);

    String inputVoucherInfo(String voucherInfo);
}

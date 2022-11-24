package com.programmers.assignment.voucher.engine.io;

import com.programmers.assignment.voucher.engine.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public interface Output {
    void findVoucherList(Map<UUID, Voucher> map);

    void exitApplication();
}

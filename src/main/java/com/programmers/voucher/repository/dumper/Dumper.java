package com.programmers.voucher.repository.dumper;

import com.programmers.voucher.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public interface Dumper {
    void dump(Map<UUID, Voucher> vouchers);
}

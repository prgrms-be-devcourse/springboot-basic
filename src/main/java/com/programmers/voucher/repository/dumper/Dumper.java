package com.programmers.voucher.repository.dumper;

import com.programmers.voucher.voucher.Voucher;

import java.util.List;

public interface Dumper {
    void dump(List<Voucher> vouchers);
}

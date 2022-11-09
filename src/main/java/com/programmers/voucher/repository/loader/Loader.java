package com.programmers.voucher.repository.loader;

import com.programmers.voucher.voucher.Voucher;

import java.util.List;

public interface Loader {
    void load(List<Voucher> vouchers);
}

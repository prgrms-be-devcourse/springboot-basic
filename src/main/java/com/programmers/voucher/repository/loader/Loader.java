package com.programmers.voucher.repository.loader;

import com.programmers.voucher.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public interface Loader {
    void load(Map<UUID, Voucher> vouchers);
}

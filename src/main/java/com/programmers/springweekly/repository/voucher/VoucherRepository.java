package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {
    void saveVoucher(Voucher voucher);
    Map<UUID, Voucher> getVoucherMap();
}

package com.programmers.springweekly.repository;

import com.programmers.springweekly.domain.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void saveVoucher(Voucher voucher);
    Optional<Map<UUID, Voucher>> getVoucherMap();
}

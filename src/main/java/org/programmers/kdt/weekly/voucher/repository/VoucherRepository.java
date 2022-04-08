package org.programmers.kdt.weekly.voucher.repository;

import org.programmers.kdt.weekly.voucher.model.Voucher;

import java.util.UUID;

public interface VoucherRepository {
    void insert(UUID voucherId, Voucher voucher);

    int getSize();

    void showAll();
}

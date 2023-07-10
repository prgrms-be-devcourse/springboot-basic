package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    Map<UUID, Voucher> findAll();

    Voucher findVoucherById(UUID uuid);
}

package com.programmers.commandLine.domain.voucher.repository;

import com.programmers.commandLine.domain.voucher.entity.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Map<UUID,Voucher> findAll();
}

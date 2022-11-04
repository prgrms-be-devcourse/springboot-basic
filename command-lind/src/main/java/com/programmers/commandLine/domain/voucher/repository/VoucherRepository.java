package com.programmers.commandLine.domain.voucher.repository;

import com.programmers.commandLine.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher save(Voucher voucher);
    Map<UUID,Voucher> findAll();
}

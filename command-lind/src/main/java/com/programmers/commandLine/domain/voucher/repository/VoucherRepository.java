package com.programmers.commandLine.domain.voucher.repository;

import com.programmers.commandLine.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
}

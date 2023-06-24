package me.kimihiqq.vouchermanagement.repository;

import me.kimihiqq.vouchermanagement.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    void deleteById(UUID voucherId);
}
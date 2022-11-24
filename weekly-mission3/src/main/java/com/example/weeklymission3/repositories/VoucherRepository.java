package com.example.weeklymission3.repositories;

import com.example.weeklymission3.models.Voucher;
import com.example.weeklymission3.models.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByTime(LocalDateTime startTime, LocalDateTime endTime);

    List<Voucher> findByType(VoucherType type);

    void deleteAll();
}

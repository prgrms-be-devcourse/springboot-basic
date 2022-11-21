package com.program.commandLine.repository;

import com.program.commandLine.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    List<Voucher> findByAssignedCustomer(UUID customerId);

    void deleteAll();

    int count();
}

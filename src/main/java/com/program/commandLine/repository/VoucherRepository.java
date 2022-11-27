package com.program.commandLine.repository;

import com.program.commandLine.model.VoucherWallet;
import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.model.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    void deleteById(UUID voucherId);

    List<Voucher> findAll();

    Voucher usedUpdate(Voucher voucher);

    void deleteAll();

    int count();

    List<Voucher> findByType(VoucherType voucherType);
}

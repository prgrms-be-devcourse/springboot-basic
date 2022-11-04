package com.programmers.commandLine.domain.voucher.repository.impl;

import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    private  Map<UUID, Voucher> voucherMemory = new LinkedHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Optional<Voucher> findVoucher = Optional.ofNullable(voucherMemory.get(voucherId));
        return findVoucher;
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherMemory.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return this.voucherMemory;
    }
}

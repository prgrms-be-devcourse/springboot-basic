package com.programmers.commandLine.domain.voucher.repository.impl;

import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.global.factory.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    private  Map<UUID, Voucher> voucherMemory = new LinkedHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        LoggerFactory.getLogger().info("VoucherRepositoryImpl save 실행");
        voucherMemory.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return this.voucherMemory;
    }
}

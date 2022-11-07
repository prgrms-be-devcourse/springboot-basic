package com.programmers.commandLine.domain.voucher.repository.impl;

import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.global.factory.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private  Map<String, Voucher> voucherMemory = new LinkedHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        LoggerFactory.getLogger().info("MemoryVoucherRepository save 실행");
        voucherMemory.put(voucher.getVoucherId().toString(), voucher);
        return voucher;
    }

    @Override
    public Map<String, Voucher> findAll() {
        return this.voucherMemory;
    }
}

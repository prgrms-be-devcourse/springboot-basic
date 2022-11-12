package com.programmers.commandline.domain.voucher.repository.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import com.programmers.commandline.global.factory.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private List<Voucher> voucherMemory = new ArrayList<>();

    @Override
    public Voucher save(Voucher voucher) {
        LoggerFactory.getLogger().info("MemoryVoucherRepository save 실행");
        voucherMemory.add(voucher);
        return voucher;

    }

    @Override
    public List<Voucher> findAll() {
        LoggerFactory.getLogger().info("MemoryVoucherRepository findAll 실행");
        return voucherMemory;
    }
}

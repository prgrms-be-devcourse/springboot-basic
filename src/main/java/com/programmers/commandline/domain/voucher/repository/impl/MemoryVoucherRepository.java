package com.programmers.commandline.domain.voucher.repository.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private List<Voucher> voucherMemory = new ArrayList<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherMemory.add(voucher);

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMemory;
    }
}

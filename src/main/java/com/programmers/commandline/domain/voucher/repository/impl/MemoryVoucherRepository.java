package com.programmers.commandline.domain.voucher.repository.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private List<Voucher> voucherMemory = new ArrayList<>();

    @Override
    public String save(Voucher voucher) {
         voucherMemory.add(voucher);
        return voucher.getVoucherId();
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMemory;
    }
}

package com.programmers.commandline.domain.voucher.repository.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile({"local"})
public class MemoryVoucherRepository implements VoucherRepository {

    private List<Voucher> memory = new ArrayList<>();

    @Override
    public String save(Voucher voucher) {
         memory.add(voucher);
        return voucher.getId();
    }

    @Override
    public List<Voucher> findAll() {
        return memory;
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        Optional<Voucher> voucher = Optional.empty();

        for (Voucher v : memory) {
            if (v.getId().equals(voucherId)) {
                voucher = Optional.of(v);
            }
        }

        return voucher;
    }
}

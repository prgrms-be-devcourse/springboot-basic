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
public class VoucherMemoryRepository implements VoucherRepository {

    private List<Voucher> memory = new ArrayList<>();

    public String save(Voucher voucher) {
        memory.add(voucher);
        return voucher.getId();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        memory.add(voucher);
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        memory.forEach(i -> {
            if (i.getId().equals(voucher.getId())) {
                i.updateDiscount(voucher.getDiscount());
            }
        });
        return voucher;
    }

    @Override
    public int count() {
        return memory.size();
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        memory.forEach(vouchers::add);
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        return memory.stream()
                .filter(voucher -> voucher.getId().equals(voucherId))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        memory.clear();
    }

    @Override
    public void deleteById(String id) {

    }
}

package com.program.commandLine.repository;

import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.model.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component(value = "voucherRepository")
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>(); // hashmap ? map ?

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }


    @Override
    public Voucher usedUpdate(Voucher voucher) {
        return voucher;
    }


    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        List<Voucher> vouchers = new ArrayList<>();
        storage.forEach((uuid, voucher) -> {
            if(Objects.equals(voucher.getVoucherType(),voucherType))
                vouchers.add(voucher);
        });
        return vouchers;
    }

}

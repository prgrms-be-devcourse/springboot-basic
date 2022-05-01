package org.prgrms.voucherapp.engine.voucher.repository;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.global.enums.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"default"})
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

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
    public List<Voucher> findAll() {
        return storage.keySet().stream()
                .map(storage::get)
                .toList();
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }

    @Override
    public List<Voucher> findByFilter(Optional<VoucherType> voucherType, Optional<LocalDateTime> after, Optional<LocalDateTime> before) {
        List<Voucher> voucherList = this.findAll();
        if(voucherType.isPresent()){
            voucherList = voucherList.stream().filter(v -> v.getTypeName().equals(voucherType.get().toString())).toList();
        }
        if(after.isPresent()){
            voucherList = voucherList.stream().filter(v -> v.getCreatedAt().isAfter(after.get())).toList();
        }
        if(before.isPresent()){
            voucherList = voucherList.stream().filter(v -> v.getCreatedAt().isBefore(before.get())).toList();
        }
        return voucherList;
    }
}

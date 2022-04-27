package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.enums.VoucherType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll( ) {
        Iterator<UUID> keys = storage.keySet().iterator();
        List<Voucher> voucherList = new ArrayList<>();
        while (keys.hasNext()) {
            voucherList.add(storage.get(keys.next()));
        }
        return voucherList;
    }

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
    public void deleteAll( ) {
        storage.clear();
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        storage.remove(voucherId);
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        List<Voucher> voucherList = findAll();
        List<Voucher> voucherTypeList = new ArrayList<>();
        for (Voucher voucher : voucherList) {
            if (voucher.getVoucherType().equals(voucherType)) {
                voucherTypeList.add(voucher);
            }
        }
        return voucherTypeList;
    }
}

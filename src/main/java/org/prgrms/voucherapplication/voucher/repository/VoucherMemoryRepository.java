package org.prgrms.voucherapplication.voucher.repository;

import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class VoucherMemoryRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    private final String NOT_FOUND = "바우처를 찾을 수 없습니다. Id값을 확인해주세요.";

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        storage.forEach((uuid, voucher) -> voucherList.add(voucher));
        return voucherList;
    }

    @Override
    public int deleteAll() {
        int size = storage.size();
        storage.clear();
        return size;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }
}

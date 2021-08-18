package org.prgrms.kdt.repository;

import org.prgrms.kdt.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    //hash map을 통한 메모리관리
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId)); //null인경우 empty반환
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }
    @Override
    public  Map<UUID,Voucher> returnAll() {
        return storage;
    }

}

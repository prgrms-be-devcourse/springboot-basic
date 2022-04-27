package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public void updateVoucherOwner(UUID voucherId, Long memberId) {
        //TODO: DB 말고 java 코드 내에서도 업데이트 이뤄지게 해야 함
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        List<Voucher> vouchers = new ArrayList<>();
        storage.values().forEach(
                voucher -> {
                    if (voucher.getType()==type) {
                        vouchers.add(voucher);
                    }
                }
        );
        return vouchers;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void remove(UUID voucherId) {
        storage.remove(voucherId);
    }

    public void clear() {
        storage.clear();
    }
}

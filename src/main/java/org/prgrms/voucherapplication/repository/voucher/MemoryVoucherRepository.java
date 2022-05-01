package org.prgrms.voucherapplication.repository.voucher;

import org.prgrms.voucherapplication.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 바우처 데이터를 메모리로 관리하는 레포지터환
 */
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    /**
     * 바우처 id를 통해 메모리에 저장된 Voucher 객체를 찾음
     *
     * @param voucherId
     * @return
     */
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    /**
     * 메모리에 Voucher 추가
     *
     * @param voucher
     * @return
     */
    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    /**
     * 메모리에 있는 모든 Voucher 객체를 List로 반환
     *
     * @return
     */
    @Override
    public List<Voucher> findAll() {
        return storage.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

package org.prgrms.springorder.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.exception.DuplicateIdException;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (storage.containsKey(voucher.getVoucherId())) {
            throw new DuplicateIdException("이미 저장되어 있는 VoucherId 입니다.");
        }

        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
            .stream()
            .toList();
    }

    @Override
    public void deleteAll() {
        this.storage.clear();
    }

}
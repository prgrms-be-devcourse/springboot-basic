package com.prgms.VoucherApp.domain.voucher.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("default")
public class VoucherMemoryDao implements VoucherDao {

    private Map<UUID, Voucher> voucherLinkedMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = voucherLinkedMap.get(voucherId);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherLinkedMap
            .values()
            .stream()
            .toList();
    }

    @Override
    public void save(Voucher voucher) {
        voucherLinkedMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void update(Voucher voucher) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }

    @Override
    public void deleteById(UUID id) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType type) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }
}

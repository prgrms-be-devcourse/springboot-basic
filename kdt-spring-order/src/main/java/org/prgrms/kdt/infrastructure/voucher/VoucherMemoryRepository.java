package org.prgrms.kdt.infrastructure.voucher;

import org.prgrms.kdt.domain.voucher.domain.Voucher;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> VOUCHER_MAP = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        // 아이디에 해당하는 바우처가 없을 경우 empty Optional 반환
        return Optional.ofNullable(VOUCHER_MAP.get(voucherId));
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return VOUCHER_MAP;
    }

    @Override
    public Optional<Voucher> save(Voucher voucher) {
        // 이미 존재하는 바우처일 경우 추가하지 않고 empty Optional 반환
        voucher = VOUCHER_MAP.putIfAbsent(voucher.getVoucherId(), voucher);
        return Optional.ofNullable(voucher);
    }
}

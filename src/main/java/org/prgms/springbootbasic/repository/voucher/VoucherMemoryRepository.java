package org.prgms.springbootbasic.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local"})
@Slf4j
public class VoucherMemoryRepository implements VoucherRepository{
    private final ConcurrentHashMap<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public Voucher upsert(Voucher voucher) {
        if (Optional.ofNullable(vouchers.get(voucher.getVoucherId())).isPresent()) {
            vouchers.replace(voucher.getVoucherId(), voucher);
        } else {
            vouchers.put(voucher.getVoucherId(), voucher);
        }
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        vouchers.remove(voucherId);
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
    }
}

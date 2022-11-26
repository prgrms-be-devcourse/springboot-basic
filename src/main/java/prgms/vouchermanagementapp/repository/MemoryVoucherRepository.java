package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException("MemoryVoucherRepository doesn't support findById()");
    }

    @Override
    public void updateVoucher(UUID voucherId, long fixedDiscountLevel) {
        throw new UnsupportedOperationException("MemoryVoucherRepository doesn't support updateVoucher()");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("MemoryVoucherRepository doesn't support deleteAll()");
    }
}

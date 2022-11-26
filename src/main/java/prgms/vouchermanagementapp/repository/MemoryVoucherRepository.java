package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    public List<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException("MemoryVoucherRepository doesn't support findById()");
    }

    @Override
    public List<Voucher> findAllByCustomerName() {
        throw new UnsupportedOperationException("MemoryVoucherRepository doesn't support findAllByCustomerName()");
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

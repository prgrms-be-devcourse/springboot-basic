package programmers.org.voucher.repository;

import org.springframework.stereotype.Repository;
import programmers.org.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        voucherStorage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> getAll() {
        return List.copyOf(voucherStorage.values());
    }
}

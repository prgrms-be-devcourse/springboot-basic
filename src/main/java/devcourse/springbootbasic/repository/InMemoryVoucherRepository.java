package devcourse.springbootbasic.repository;

import devcourse.springbootbasic.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherDatabase = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherDatabase.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherDatabase.values()
                .stream()
                .toList();
    }
}

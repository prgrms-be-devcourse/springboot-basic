package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
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

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherDatabase.get(voucherId));
    }

    @Override
    public int update(Voucher voucher) {
        int updatedRow = voucherDatabase.containsKey(voucher.getId()) ? 1 : 0;
        voucherDatabase.put(voucher.getId(), voucher);
        return updatedRow;
    }

    @Override
    public int delete(Voucher voucher) {
        int deletedRow = voucherDatabase.containsKey(voucher.getId()) ? 1 : 0;
        voucherDatabase.remove(voucher.getId());
        return deletedRow;
    }
}

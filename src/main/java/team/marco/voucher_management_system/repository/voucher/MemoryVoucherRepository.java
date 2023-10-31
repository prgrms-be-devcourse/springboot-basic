package team.marco.voucher_management_system.repository.voucher;

import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.domain.voucher.Voucher;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<Long, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values().stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        if(voucherMap.containsKey(voucherId)) {
            return Optional.of(voucherMap.get(voucherId));
        } else return Optional.empty();
    }

    @Override
    public void deleteById(Long voucherId) {
        voucherMap.remove(voucherId);
    }

    @Override
    public Optional<Long> findLatestVoucherId() {
        return voucherMap.keySet().stream()
                .max(Comparator.naturalOrder());
    }
}

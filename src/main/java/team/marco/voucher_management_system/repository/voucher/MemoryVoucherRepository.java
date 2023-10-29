package team.marco.voucher_management_system.repository.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.model.voucher.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile({"local", "debug"})
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

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
    public List<Voucher> findByOwner(UUID ownerId) {
        return voucherMap.values().stream()
                .filter(v -> v.getOwnerId() == ownerId)
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if(voucherMap.containsKey(voucherId)) {
            return Optional.of(voucherMap.get(voucherId));
        } else return Optional.empty();
    }

    @Override
    public Voucher update(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);

        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        voucherMap.remove(voucherId);
    }
}

package team.marco.voucher_management_system.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.type_enum.VoucherType;

@Profile({"debug", "test"})
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        if (!voucherMap.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(voucherMap.get(id));
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return voucherMap.values()
                .stream()
                .filter(voucher -> voucher.isSameType(voucherType))
                .toList();
    }
}

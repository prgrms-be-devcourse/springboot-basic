package team.marco.voucher_management_system.console_app.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.VoucherRepository;
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
    public int deleteById(UUID id) {
        if (!voucherMap.containsKey(id)) {
            return 0;
        }

        voucherMap.remove(id);

        return 1;
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

    @Override
    public List<Voucher> findByCreateAt(LocalDateTime from, LocalDateTime to) {
        return findAll().stream()
                .filter(voucher -> voucher.isCreatedBetween(from, to))
                .toList();
    }
}

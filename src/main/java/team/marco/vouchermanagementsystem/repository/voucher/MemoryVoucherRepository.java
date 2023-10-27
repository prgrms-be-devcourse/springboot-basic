package team.marco.vouchermanagementsystem.repository.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.voucher.Voucher;

import java.util.List;
import java.util.Map;
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
}

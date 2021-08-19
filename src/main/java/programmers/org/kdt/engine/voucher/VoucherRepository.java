package programmers.org.kdt.engine.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Optional<Voucher> insert(Voucher voucher);

    Set<Map.Entry<UUID, Voucher>> getAllEntry();
}

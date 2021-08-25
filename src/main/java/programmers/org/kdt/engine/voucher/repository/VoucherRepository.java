package programmers.org.kdt.engine.voucher.repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import programmers.org.kdt.engine.voucher.type.Voucher;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Optional<Voucher> insert(Voucher voucher);

    Set<Map.Entry<UUID, Voucher>> getAllEntry();
}

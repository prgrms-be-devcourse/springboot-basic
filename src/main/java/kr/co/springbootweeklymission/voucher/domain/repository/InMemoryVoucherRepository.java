package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotSupportedException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("memory")
@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> VOUCHER_MEMORY = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        VOUCHER_MEMORY.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(VOUCHER_MEMORY.get(voucherId));
    }

    @Override
    public List<Voucher> findVouchersByMemberId(UUID memberId) {
        throw new NotSupportedException(ResponseStatus.FAIL_NOT_SUPPORTED_READ);
    }

    @Override
    public List<Voucher> findAll() {
        return VOUCHER_MEMORY.values()
                .stream()
                .toList();
    }

    @Override
    public void update(Voucher voucher) {
        throw new NotSupportedException(ResponseStatus.FAIL_NOT_SUPPORTED_UPDATE);
    }

    @Override
    public void deleteById(UUID voucherId) {
        throw new NotSupportedException(ResponseStatus.FAIL_NOT_SUPPORTED_DELETE);
    }

    public void clear() {
        VOUCHER_MEMORY.clear();
    }
}

package prgms.springbasic.voucher;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> repository = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher) {
        repository.put(voucher.getVoucherId(), voucher);
        return repository.get(voucher.getVoucherId());
    }

    @Override
    public List<Voucher> getVoucherList() {
        return new ArrayList<>(repository.values());
    }
}
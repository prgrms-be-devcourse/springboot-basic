package org.prgrms.kdt.assignments;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(vouchers.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        vouchers.put(voucher.getVoucherID(), voucher);
        return vouchers.get(voucher.getVoucherID());
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return Collections.unmodifiableMap(vouchers);
    }




}

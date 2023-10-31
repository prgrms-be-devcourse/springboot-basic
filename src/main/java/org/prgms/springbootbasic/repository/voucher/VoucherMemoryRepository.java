package org.prgms.springbootbasic.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local"})
@Slf4j
public class VoucherMemoryRepository implements VoucherRepository{
    private final ConcurrentHashMap<UUID, VoucherPolicy> vouchers = new ConcurrentHashMap<>();

    @Override
    public Optional<VoucherPolicy> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public List<VoucherPolicy> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public VoucherPolicy upsert(VoucherPolicy voucherPolicy) {
        if (Optional.ofNullable(vouchers.get(voucherPolicy.getVoucherId())).isPresent()) {
            vouchers.replace(voucherPolicy.getVoucherId(), voucherPolicy);
        } else {
            vouchers.put(voucherPolicy.getVoucherId(), voucherPolicy);
        }
        return voucherPolicy;
    }

    @Override
    public void deleteById(UUID voucherId) {
        vouchers.remove(voucherId);
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
    }
}

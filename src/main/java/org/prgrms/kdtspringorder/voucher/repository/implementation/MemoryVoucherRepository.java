package org.prgrms.kdtspringorder.voucher.repository.implementation;

import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdtspringorder.voucher.repository.abstraction.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    Map<UUID, Voucher> memoryDB = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if (!this.memoryDB.containsKey(voucherId)) return Optional.empty();
        return Optional.of(this.memoryDB.get(voucherId));
    }

    @Override
    public List<Voucher> getVouchers() {
        return new ArrayList<>(this.memoryDB.values());
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        Objects.requireNonNull(voucher);

        UUID generatedId = this.generateId();
        voucher.assignId(generatedId); // Voucher가 이미 ID를 가지고 있다면, ID는 바뀌지 않는다.
        this.memoryDB.put(voucher.getId(), voucher);
        return voucher;
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }
}

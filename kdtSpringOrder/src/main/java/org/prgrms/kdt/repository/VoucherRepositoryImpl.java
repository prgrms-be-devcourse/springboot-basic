package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.*;

public class VoucherRepositoryImpl implements VoucherRepository{

    private final Map<UUID, Voucher> memoryVoucherRepository = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        return memoryVoucherRepository.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(memoryVoucherRepository.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memoryVoucherRepository.values());
    }
}
package org.prgrms.java.repository.voucher;

import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;
import org.prgrms.java.exception.notfound.VoucherNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByCustomer(UUID customerId) {
        return storage.values().stream()
                .filter(voucher -> voucher.getOwnerId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findExpiredVouchers() {
        return storage.values().stream()
                .filter(voucher -> voucher.getExpiredAt().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isPresent()) {
            throw new VoucherBadRequestException("이미 존재하는 아이디입니다.");
        }
        storage.put(voucher.getVoucherId(), voucher);
        return storage.get(voucher.getVoucherId());
    }

    @Override
    public Voucher update(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isEmpty()) {
            throw new VoucherNotFoundException();
        }
        storage.put(voucher.getVoucherId(), voucher);
        return storage.get(voucher.getVoucherId());
    }

    @Override
    public void delete(UUID voucherId) {
        if (findById(voucherId).isEmpty()) {
            throw new VoucherNotFoundException();
        }
        storage.remove(voucherId);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}

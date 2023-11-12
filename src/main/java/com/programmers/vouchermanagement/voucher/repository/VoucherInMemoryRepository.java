package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.vouchermanagement.util.Message.NOT_DELETED;
import static com.programmers.vouchermanagement.util.Message.NOT_UPDATED;

@Repository
@Profile("memory")
public class VoucherInMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers;

    public VoucherInMemoryRepository() {
        vouchers = new HashMap<>();
    }

    @Override
    public void insert(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public List<Voucher> findAllByCreatedAt(LocalDateTime from, LocalDateTime to) {
        return vouchers.values().stream().filter(voucher -> isCreatedBetweenPeriod(voucher, from, to)).toList();
    }

    private boolean isCreatedBetweenPeriod(Voucher voucher, LocalDateTime from, LocalDateTime to) {
        LocalDateTime createdAt = voucher.getCreatedAt();
        return createdAt.isAfter(from) && createdAt.isBefore(to);
    }

    @Override
    public void delete(UUID id) {
        Optional.ofNullable(vouchers.remove(id)).orElseThrow(() -> new NoSuchElementException(NOT_DELETED));
    }

    @Override
    public void deleteAll() {
        if (!vouchers.isEmpty()) vouchers.clear();
    }

    @Override
    public void update(Voucher voucher) {
        Optional.ofNullable(vouchers.get(voucher.getId())).orElseThrow(() -> new NoSuchElementException(NOT_UPDATED));
        vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAllByType(VoucherType voucherType) {
        return vouchers.values().stream().filter(voucher -> voucher.getTypeName().equals(voucherType.getName())).toList();
    }
}

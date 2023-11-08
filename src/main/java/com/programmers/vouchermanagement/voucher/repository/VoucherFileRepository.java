package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherType;
import com.programmers.vouchermanagement.voucher.repository.util.VoucherFileManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.vouchermanagement.util.Message.NOT_DELETED;
import static com.programmers.vouchermanagement.util.Message.NOT_UPDATED;

@Repository
@Profile("file")
public class VoucherFileRepository implements VoucherRepository {
    public final Map<UUID, Voucher> vouchers;
    private final VoucherFileManager voucherFileManager;

    public VoucherFileRepository(VoucherFileManager voucherFileManager) {
        this.voucherFileManager = voucherFileManager;
        vouchers = voucherFileManager.loadFile();
    }

    @Override
    public void insert(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        voucherFileManager.saveFile(vouchers);
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
        return vouchers.values()
                .stream()
                .filter(voucher -> {
                    LocalDateTime createdAt = voucher.getCreatedAt();
                    return createdAt.isAfter(from) && createdAt.isBefore(to);
                })
                .toList();
    }

    @Override
    public void delete(UUID id) {
        Optional.ofNullable(vouchers.remove(id))
                .orElseThrow(() -> new NoSuchElementException(NOT_DELETED));
        voucherFileManager.saveFile(vouchers);
    }

    @Override
    public void deleteAll() {
        if (!vouchers.isEmpty()) vouchers.clear();
        voucherFileManager.saveFile(vouchers);
    }

    @Override
    public void update(Voucher voucher) {
        Optional.ofNullable(vouchers.get(voucher.getId()))
                .orElseThrow(() -> new NoSuchElementException(NOT_UPDATED));
        vouchers.put(voucher.getId(), voucher);
        voucherFileManager.saveFile(vouchers);
    }

    @Override
    public List<Voucher> findAllByType(VoucherType voucherType) {
        return vouchers.values()
                .stream()
                .filter(voucher -> voucher.getTypeName().equals(voucherType.getName()))
                .toList();
    }
}

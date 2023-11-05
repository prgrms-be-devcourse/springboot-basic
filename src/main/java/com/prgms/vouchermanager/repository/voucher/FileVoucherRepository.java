package com.prgms.vouchermanager.repository.voucher;


import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.voucher.VoucherType;
import com.prgms.vouchermanager.util.file.FileManager;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {
    private final FileManager fileManager;

    private final Map<UUID, Voucher> vouchers;

    public FileVoucherRepository(FileManager fileManager) {
        this.fileManager = fileManager;
        vouchers = fileManager.readVoucherCsv();

    }

    @Override
    public Voucher save(Voucher voucher) {
        return vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public void update(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            Voucher voucher = vouchers.get(id);
            return Optional.of(voucher);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public void deleteById(UUID id) {
        vouchers.remove(id);
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
    }

    @Override
    public List<Voucher> findByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return vouchers.values().stream()
                .filter(voucher -> voucher.getCreatedAt().isAfter(startTime) && voucher.getCreatedAt().isBefore(endTime))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        return vouchers.values().stream()
                .filter(voucher -> voucher.getVoucherType().equals(VoucherType.FIXED_AMOUNT))
                .collect(Collectors.toUnmodifiableList());
    }


    @PreDestroy
    public void saveRepository() {
        fileManager.saveVoucherFile(vouchers);
    }
}


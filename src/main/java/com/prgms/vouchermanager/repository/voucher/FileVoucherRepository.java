package com.prgms.vouchermanager.repository.voucher;


import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.util.file.FileManager;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    @PreDestroy
    public void saveRepository() {
        fileManager.saveVoucherFile(vouchers);
    }
}


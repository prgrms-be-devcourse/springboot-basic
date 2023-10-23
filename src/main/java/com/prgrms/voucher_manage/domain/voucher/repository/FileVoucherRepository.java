package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.file.VoucherFileManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private final VoucherFileManager voucherFileManager;
    private final Map<UUID, Voucher> storage;

    public FileVoucherRepository(VoucherFileManager voucherFileManager) {
        this.voucherFileManager = voucherFileManager;
        storage = voucherFileManager.loadData();
    }

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        voucherFileManager.updateFile(storage);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    public void clear(){
        storage.clear();
        voucherFileManager.updateFile(storage);
    }
}

package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.util.VoucherFileManager;
import org.springframework.beans.factory.annotation.Value;
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
        storage = voucherFileManager.loadVoucherData();
    }

    @Override
    public void insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        voucherFileManager.updateFile(storage);
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }
}

package com.prgms.vouchermanager.repository.voucher;


import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.util.file.FileManager;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository{
    private final FileManager fileManager;

    private Map<UUID,Voucher>vouchers ;

    public FileVoucherRepository(FileManager fileManager) {
        this.fileManager = fileManager;
        vouchers = fileManager.readVoucherCsv();


    }

    @Override
    public void create(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return new ArrayList<>(vouchers.values());
    }

    @PreDestroy
    public void saveRepository() {
        fileManager.saveVoucherFile(vouchers);
    }
}

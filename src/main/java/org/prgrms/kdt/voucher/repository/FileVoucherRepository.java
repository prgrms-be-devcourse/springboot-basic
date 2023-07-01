package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.VoucherLoader;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.*;

@Profile("file")
@Component
public class FileVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage;
    private final VoucherLoader voucherLoader;

    public FileVoucherRepository(VoucherLoader voucherLoader) {
        this.voucherLoader = voucherLoader;
        this.storage = this.voucherLoader.loadFileToMemoryVoucher();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(storage.values());
    }

    @PreDestroy
    public void fileWrite() {
        voucherLoader.saveMemoryVoucherToFile(storage);
    }
}

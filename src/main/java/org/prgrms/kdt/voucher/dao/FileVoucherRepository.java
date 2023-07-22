package org.prgrms.kdt.voucher.dao;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


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

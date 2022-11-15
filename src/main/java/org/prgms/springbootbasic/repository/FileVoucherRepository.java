package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.domain.Voucher;
import org.prgms.springbootbasic.util.VoucherFileManipulator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> memoryCache;
    private final VoucherFileManipulator voucherFileManipulator;

    public FileVoucherRepository(VoucherFileManipulator voucherFileManipulator) {
        this.voucherFileManipulator = voucherFileManipulator;
        this.voucherFileManipulator.initFile();
        memoryCache = this.voucherFileManipulator.getMemoryCache();
    }

    @PreDestroy
    public void saveFile() {
        voucherFileManipulator.writeFile(memoryCache);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memoryCache.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        memoryCache.putIfAbsent(voucher.getUuid(), voucher);
        return voucher;
    }
}

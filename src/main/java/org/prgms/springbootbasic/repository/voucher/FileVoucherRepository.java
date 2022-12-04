package org.prgms.springbootbasic.repository.voucher;

import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.util.VoucherFileManipulator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.*;


@Repository
@Profile("file")
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
        memoryCache.putIfAbsent(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public UUID deleteById(UUID voucherId) {
        memoryCache.remove(voucherId);
        return voucherId;
    }


    @Override
    public UUID deleteByCustomerId(UUID customerId) {
        for (Map.Entry<UUID, Voucher> set : memoryCache.entrySet()) {
            if (set.getValue().getCustomerId().equals(customerId)) {
                memoryCache.remove(set.getKey());
            }
        }
        return customerId;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        for (Map.Entry<UUID, Voucher> set : memoryCache.entrySet()) {
            if (set.getValue().getVoucherId().equals(voucherId)) {
                return Optional.of(set.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        List<Voucher> result = new ArrayList<>();
        for (Map.Entry<UUID, Voucher> set : memoryCache.entrySet()) {
            if (set.getValue().getCustomerId().equals(customerId)) {
                result.add(set.getValue());
            }
        }
        return result;
    }

    @Override
    public Voucher updateByCustomerId(Voucher voucher) {
        return voucher;
    }

    @Override
    public UUID updateByCustomerId(UUID customerId, UUID voucherID) {
        return customerId;
    }
}

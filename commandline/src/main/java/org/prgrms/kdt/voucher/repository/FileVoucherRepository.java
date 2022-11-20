package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.io.CSVInOut;
import org.prgrms.kdt.util.VoucherType;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {
    static private Map<Long, Voucher> cache = new ConcurrentHashMap<>();

    private static long VOUCHER_ID = 0;

    private final CSVInOut csvInOut;

    public FileVoucherRepository(@Value("${voucher.path}") String path) {
        this.csvInOut = new CSVInOut(path);
    }

    @Override
    public synchronized Voucher insert(String type, long discountDegree) {
        Voucher createVoucher = VoucherType.createVoucher(type, ++VOUCHER_ID, discountDegree);
        csvInOut.writeCSV(createVoucher);
        cache.put(createVoucher.getVoucherId(), createVoucher);
        return createVoucher;
    }

    @Override
    public List<Voucher> findAll() {
        return csvInOut.readAll();
    }

    @Override
    public Voucher findById(Long voucherId) {
        if (cache.containsKey(voucherId)) {
            return cache.get(voucherId);
        }
        return csvInOut.findVoucher(voucherId);

    }

    @Override
    public synchronized void update(Long voucherId, long discountDegree) {
        csvInOut.voucherUpdate(voucherId, discountDegree);
        Voucher oldVoucher = cache.get(voucherId);
        Voucher newVoucher = oldVoucher.changeDiscountDegree(discountDegree);
        cache.replace(voucherId, newVoucher);
    }

    @Override
    public void deleteAll() {
        csvInOut.deleteAllFile();
        cache.clear();
    }
}

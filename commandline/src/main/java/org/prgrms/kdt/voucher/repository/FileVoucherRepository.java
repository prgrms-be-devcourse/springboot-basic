package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundVoucherException;
import org.prgrms.kdt.io.CSVInOut;
import org.prgrms.kdt.voucher.VoucherType;
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
        init(this.csvInOut);
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
    public Voucher findById(long voucherId) {
        if (cache.containsKey(voucherId)) {
            return cache.get(voucherId);
        }
        return csvInOut.findVoucher(voucherId);

    }

    @Override
    public synchronized void update(long voucherId, long discountDegree) {
        List<Voucher> vouchers = csvInOut.readAll();
        for (int i = 0; i < vouchers.size(); i++) {
            Voucher voucher = vouchers.get(i);
            if (matchId(voucher, voucherId)) {
                Voucher newVoucher = voucher.changeDiscountDegree(discountDegree);
                vouchers.set(i, newVoucher);
                cache.replace(voucherId, newVoucher);
                csvInOut.voucherUpdate(vouchers);
                return;
            }
        }

        throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
    }

    @Override
    public void deleteAll() {
        csvInOut.deleteAllFile();
        cache.clear();
    }

    private boolean matchId(Voucher compareVoucher, long newVoucherId) {
        return compareVoucher.getVoucherId() == newVoucherId;
    }

    private void init(CSVInOut csvInOut) {
        List<Voucher> vouchers = csvInOut.readAll();
        for (Voucher voucher : vouchers) {
            long current = voucher.getVoucherId();
            if (current > VOUCHER_ID) {
                VOUCHER_ID = current;
            }
        }
    }
}

package org.devcourse.voucher.repository;

import org.devcourse.voucher.domain.voucher.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryVoucherRepository implements VoucherRepository {

    private final Map<Long, Voucher> voucherStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Voucher save(Voucher voucher) {
        validateInputVoucher(voucher);

        return saveWithSequenceId(voucher);
    }

    private void validateInputVoucher(Voucher voucher) {
        if (voucher == null) {
            throw new RuntimeException("빈 값을 저장 할 수 없습니다");
        }
    }

    private Voucher saveWithSequenceId(Voucher voucher) {
        long id = idGenerator.getAndIncrement();
        Voucher saveVoucher = voucher.newInstanceWithId(id);
        voucherStorage.put(saveVoucher.getId(), saveVoucher);

        return saveVoucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherStorage.values());
    }

}

package org.devcourse.voucher.repository;

import org.devcourse.voucher.domain.voucher.Voucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryVoucherRepository implements VoucherRepository{

    private final static Map<Long, Voucher> voucherStorage = new HashMap<>();
    private final static AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new RuntimeException("빈 값을 저장 할 수 없습니다");
        }

        Voucher saveVoucher = voucher;
        if (voucher.getId() == 0) {
            long id = idGenerator.getAndIncrement();
            saveVoucher = new Voucher(id, voucher.getType(), voucher.getPolicyAmount());
        }

        voucherStorage.put(saveVoucher.getId(), saveVoucher);

        return saveVoucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherStorage.values());
    }

    @Override
    public void deleteAll() {
        voucherStorage.clear();
    }

}

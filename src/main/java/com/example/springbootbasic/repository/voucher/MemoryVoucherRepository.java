package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private static final Map<Long, Voucher> storage = new ConcurrentHashMap<>();
    private static long sequence;

    @Override
    public synchronized Voucher save(Voucher voucher) {
        try {
            Voucher generatedVoucher = VoucherFactory.of(
                    ++sequence, voucher.getDiscountValue(), voucher.getVoucherType());
        } catch () {

        }
        storage.put(generatedVoucher.getVoucherId(), generatedVoucher);
        return generatedVoucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private static final Map<Long, Voucher> storage = new ConcurrentHashMap<>();
    private static long sequence;

    @Override
    public synchronized Voucher save(Voucher voucher) {
        try {
            Voucher generatedVoucher = VoucherFactory.of(
                    ++sequence, voucher.getVoucherDiscountValue(), voucher.getVoucherType());
            storage.put(generatedVoucher.getVoucherId(), generatedVoucher);
            return generatedVoucher;
        } catch (IllegalArgumentException e) {
            logger.error("Fail - {}", e.getMessage());
            return voucher;
        }
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Voucher> findAllVouchersByVoucherType(VoucherType voucherType) {
        return storage.values()
                .stream()
                .filter(voucher -> voucher.getVoucherType() == voucherType)
                .collect(Collectors.toList());
    }

    @Override
    public Voucher findById(long voucherId) {
        if (!storage.containsKey(voucherId)) {
            throw new IllegalArgumentException();
        }
        return storage.get(voucherId);
    }

    @Override
    public void deleteAllVouchers() {
        storage.clear();
    }
}
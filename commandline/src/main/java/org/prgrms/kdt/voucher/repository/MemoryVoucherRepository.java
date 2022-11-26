package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.voucher.NotFoundVoucherException;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<Long, Voucher> storage = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Voucher insert(String type, long discountDegree) {
        Voucher voucher;
        synchronized (this) {
            voucher = VoucherType.createVoucher(type, ++id, discountDegree);
        }
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher findById(long voucherId) {
        if (!storage.containsKey(voucherId)) {
            throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
        }
        return storage.get(voucherId);
    }

    @Override
    public void update(long voucherId, long discountDegree) {
        if (!storage.containsKey(voucherId)) {
            throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
        }

        changVoucher(voucherId, discountDegree);
    }

    private void changVoucher(long voucherId, long discountDegree) {
        Voucher oldVoucher = storage.get(voucherId);
        Voucher newVoucher = oldVoucher.changeDiscountDegree(discountDegree);
        storage.replace(voucherId, newVoucher);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public void deleteById(long voucherId) {
        if (!storage.containsKey(voucherId)) {
            throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
        }
        storage.remove(voucherId);
    }

    @Override
    public List<Voucher> findByTypeName(String typeNumber) {
        String voucherTypeName = VoucherType.getVoucherTypeName(typeNumber);

        List<Voucher> vouchers = storage.values().stream()
                .filter(voucher -> voucher.getTypeName().equals(voucherTypeName))
                .collect(Collectors.toList());

        return vouchers;
    }
}

package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public void updateVoucherOwner(UUID voucherId, Long memberId) {
        var foundVoucher = storage.get(voucherId);
        storage.remove(voucherId);

        Voucher voucherToBeInserted = null;
        if (foundVoucher.getType() == FIXED) {
            voucherToBeInserted = new FixedDiscountVoucher(
                    foundVoucher.getId(), foundVoucher.getAmount(), memberId, foundVoucher.getRegisteredAt());
        }
        if (foundVoucher.getType() == RATE) {
            voucherToBeInserted = new FixedDiscountVoucher(
                    foundVoucher.getId(), foundVoucher.getAmount(), memberId, foundVoucher.getRegisteredAt());
        }
        storage.put(voucherId, voucherToBeInserted);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        List<Voucher> vouchers = new ArrayList<>();
        storage.values().forEach(
                voucher -> {
                    if (voucher.getType() == type) {
                        vouchers.add(voucher);
                    }
                }
        );
        return vouchers;
    }

    @Override
    public List<Voucher> findByDate(Date startingDate, Date endingDate) {
        List<Voucher> vouchers = new ArrayList<>();
        storage.values().forEach(
                voucher -> {
                    if (isInThePeriod(voucher.getRegisteredAt(), startingDate, endingDate)) {
                        vouchers.add(voucher);
                    }
                }
        );
        return vouchers;
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, Date startingDate, Date endingDate) {
        List<Voucher> vouchers = new ArrayList<>();
        storage.values().forEach(
                voucher -> {
                    if (voucher.getType() == type && isInThePeriod(voucher.getRegisteredAt(), startingDate, endingDate)) {
                        vouchers.add(voucher);
                    }
                }
        );
        return vouchers;
    }

    private boolean isInThePeriod(Timestamp compared, Date startingDate, Date endingDate) {
        return startingDate.before(compared) && endingDate.after(compared);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void remove(UUID voucherId) {
        storage.remove(voucherId);
    }

    public void clear() {
        storage.clear();
    }
}

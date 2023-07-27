package org.devcourse.springbasic.domain.voucher.dao;

import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("local")
@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(storage.get(voucherId));
        } catch (NullPointerException nullPointerException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCriteria(LocalDate creationStartDate,
                                        LocalDate creationEndDate,
                                        VoucherType voucherType) {
        List<Voucher> findVouchers = new ArrayList<>();
        Collection<Voucher> vouchers = storage.values();
        for (Voucher voucher : vouchers) {
            boolean isCreationStartDateValid = (creationStartDate == null
                    || voucher.getCreatedAt().toLocalDate().isAfter(creationStartDate));
            boolean isCreationEndDateValid = (creationEndDate == null
                    || voucher.getCreatedAt().toLocalDate().isBefore(creationEndDate));
            boolean isVoucherTypeValid = (voucherType == null
                    || voucher.getVoucherType().equals(voucherType));

            if (isCreationStartDateValid && isCreationEndDateValid && isVoucherTypeValid) {
                findVouchers.add(voucher);
            }
        }
        return findVouchers;
    }

    @Override
    public UUID save(Voucher voucher) {

        try {
            storage.put(voucher.getVoucherId(), voucher);
            return voucher.getVoucherId();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("이미 존재하는 바우처입니다.");
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        try {
            storage.remove(customerId);
        } catch (NullPointerException e) {
            throw new NoSuchElementException("삭제할 바우처가 없습니다.");
        }
    }
}

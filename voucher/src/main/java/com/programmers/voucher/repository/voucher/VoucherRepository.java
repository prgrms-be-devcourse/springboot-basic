package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    void loadVouchers();

    void persistVouchers();

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(long id);

    Optional<Voucher> findByIdAndCustomer(long id, long customerId);

    List<Voucher> findAllByCustomer(long customerId);

    List<Voucher> listAll();

    default List<Voucher> listAllBetween(LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException("Filtered search not supported.");
    }

    default List<Voucher> listAllByVoucherType(DiscountPolicy.Type type) {
        throw new UnsupportedOperationException("Filtered search not supported.");
    }

    default List<Voucher> listAllBetweenByVoucherType(LocalDate from, LocalDate to, DiscountPolicy.Type type) {
        throw new UnsupportedOperationException("Filtered search not supported.");
    }

    Voucher update(Voucher voucher);

    void deleteById(long id);
}

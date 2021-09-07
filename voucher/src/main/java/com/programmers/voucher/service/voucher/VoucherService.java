package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoucherService {
    void openStorage();
    void closeStorage();

    Voucher create(String name, DiscountType type, int value, long customerId);
    Optional<Voucher> findById(long id);
    List<Voucher> listAll();
    List<Voucher> listAll(LocalDate from, LocalDate to);
    List<Voucher> listAll(LocalDate from, LocalDate to, Voucher.SearchCriteria searchCriteria, String keyword);

    Voucher update(Voucher voucher);
    void delete(long voucherId);
}

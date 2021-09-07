package com.programmers.voucher.entity.voucher;

import com.programmers.voucher.repository.voucher.VoucherRepository;

import java.time.LocalDate;
import java.util.List;

@FunctionalInterface
public interface DateBasedVoucherSearch {
    List<Voucher> search(VoucherRepository repository, LocalDate from, LocalDate to, String value);
}

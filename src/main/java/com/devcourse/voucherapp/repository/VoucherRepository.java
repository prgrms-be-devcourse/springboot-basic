package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAllVouchers();

    Optional<Voucher> findVoucherById(String id);

    Voucher update(Voucher voucher);

    int delete(String id);
}

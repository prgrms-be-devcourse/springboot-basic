package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.wrapper.Username;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> getAssignedVoucherListByUsername(Username username);

    List<Voucher> getNotAssignedVoucherList();

    List<Voucher> getAssignedVoucherList();

    List<Voucher> findByCreationTimeAndDiscountType(LocalDateTime startDate, LocalDateTime endDate, int discountType);

    int removeVoucher(UUID voucherUUID);

    Voucher findById(UUID voucherUUID);
}

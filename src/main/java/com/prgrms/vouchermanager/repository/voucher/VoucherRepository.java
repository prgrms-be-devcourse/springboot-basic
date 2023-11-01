package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    List<Voucher> findAll();

    Voucher findById(UUID id);

    Voucher updateDiscount(Voucher updateVoucher);

    int delete(UUID id);

    List<Voucher> findByDate(String start, String end);

    List<Voucher> findByDateAndVoucherType(VoucherType voucherType, String start, String end);
}

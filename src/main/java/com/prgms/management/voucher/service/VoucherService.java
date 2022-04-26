package com.prgms.management.voucher.service;

import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface VoucherService {
    List<Voucher> findVouchers(VoucherType type, Timestamp start, Timestamp end);

    Voucher addVoucher(Voucher voucher);

    Voucher findVoucherById(UUID id);

    void removeVoucherById(UUID id);
}

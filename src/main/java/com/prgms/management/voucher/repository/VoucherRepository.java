package com.prgms.management.voucher.repository;

import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher findById(UUID voucherId) throws VoucherException;

    List<Voucher> findAll() throws VoucherException;

    Voucher save(Voucher voucher);
}

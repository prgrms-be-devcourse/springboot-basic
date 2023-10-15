package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.common.VoucherType;
import org.prgms.springbootbasic.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher findById(UUID voucherId);
    List<Voucher> findAll();
    Voucher create(VoucherType type, int val);
}

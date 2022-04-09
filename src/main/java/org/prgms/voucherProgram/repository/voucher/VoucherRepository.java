package org.prgms.voucherProgram.repository.voucher;

import java.util.List;

import org.prgms.voucherProgram.entity.voucher.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}

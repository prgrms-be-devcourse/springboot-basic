package org.voucherProject.voucherProject.voucher.service;

import org.voucherProject.voucherProject.voucher.entity.Voucher;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher getVoucher(UUID voucherId);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);

}

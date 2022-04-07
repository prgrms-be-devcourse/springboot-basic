package org.voucherProject.voucherProject.service;

import org.voucherProject.voucherProject.entity.voucher.Voucher;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher getVoucher(UUID voucherId);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);

}

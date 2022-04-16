package org.voucherProject.voucherProject.voucher.service;

import org.voucherProject.voucherProject.voucher.entity.Voucher;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher save(Voucher voucher);

    Voucher getVoucher(UUID voucherId);

    List<Voucher> findAll();

    Voucher updateVoucher(Voucher voucher);

}

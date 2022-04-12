package org.voucherProject.voucherProject.controller.voucher;

import org.voucherProject.voucherProject.entity.voucher.Voucher;
import java.util.List;
import java.util.UUID;

public interface VoucherController {

    Voucher createVoucher(Voucher voucher);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);

}

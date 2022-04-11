package org.voucherProject.voucherProject.controller.voucher;

import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherController {

    Voucher createVoucher(VoucherType voucherType, long number);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);

}

package org.voucherProject.voucherProject.controller.voucher;

import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VoucherController {

    Voucher createVoucher(VoucherDto voucherDto);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);

    void printAll();

}

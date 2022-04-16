package org.voucherProject.voucherProject.voucher.controller;

import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherDto;
import java.util.List;

public interface VoucherController {

    Voucher createVoucher(VoucherDto voucherDto);

    List<VoucherDto> findAll();

    VoucherDto findById(VoucherDto voucherDto);

}

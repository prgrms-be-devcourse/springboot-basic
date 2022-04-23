package org.voucherProject.voucherProject.voucher.controller;

import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.controller.dto.VoucherDto;
import java.util.List;

public interface VoucherController {

    Voucher createVoucher(VoucherDto voucherDto);

    VoucherDto findById(VoucherDto voucherDto);

    List<VoucherDto> findAll();

    void useVoucher(VoucherDto voucherDto);

}

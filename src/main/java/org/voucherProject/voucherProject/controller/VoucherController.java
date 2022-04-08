package org.voucherProject.voucherProject.controller;

import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherController {

    Voucher createVoucher(VoucherType voucherType, long number) throws IOException;

    List<Voucher> findAll() throws IOException;

    Voucher findById(UUID voucherId) throws IOException;

}

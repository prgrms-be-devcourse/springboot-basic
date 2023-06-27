package me.kimihiqq.vouchermanagement.domain.voucher.service;

import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(VoucherDto voucherDto);
    List<Voucher> listVouchers();
}

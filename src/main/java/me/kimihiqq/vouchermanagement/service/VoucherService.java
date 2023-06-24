package me.kimihiqq.vouchermanagement.service;

import me.kimihiqq.vouchermanagement.domain.Voucher;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(VoucherDto voucherDto);
    List<Voucher> listVouchers();
}

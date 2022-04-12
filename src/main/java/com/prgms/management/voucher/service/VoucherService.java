package com.prgms.management.voucher.service;

import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;

import java.util.List;

public interface VoucherService {
    List<Voucher> getAllVouchers() throws VoucherException;

    Voucher saveVoucher(Voucher voucher) throws VoucherException;
}

package org.prgrms.weeklymission.voucher.service;

import org.prgrms.weeklymission.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    Voucher createVoucher(String option, String discount);
    Voucher findVoucherById(UUID voucherId);
    List<Voucher> searchAllVouchers();
    void printAllVouchers();
    void printCreateVoucher();
    void clearRepo();
}
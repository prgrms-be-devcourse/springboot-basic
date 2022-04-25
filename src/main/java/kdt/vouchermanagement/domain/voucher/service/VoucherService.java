package kdt.vouchermanagement.domain.voucher.service;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;

import java.util.List;

public interface VoucherService {

    Voucher createVoucher(Voucher voucher);

    List<Voucher> findVouchers();
}

package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.Duration;

import java.util.List;

public interface VoucherService {
    Voucher saveVoucher(Voucher voucher);

    List<Voucher> findAllVoucher();

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findByDuration(Duration duration);

    Voucher findVoucherById(long id);

    void deleteVoucherById(long id);

    List<Voucher> findByTypeDuration(VoucherType voucherType, Duration duration);
}

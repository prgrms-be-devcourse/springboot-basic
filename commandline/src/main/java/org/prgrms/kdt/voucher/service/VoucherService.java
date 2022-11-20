package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;

public interface VoucherService {

    List<Voucher> getAllVoucher();

    Voucher createVoucher(String type, long discountDegree);

    void updateVoucher(Long voucherId, long voucherDegree);

    Voucher findById(Long voucherId);

    void deleteAll();

}

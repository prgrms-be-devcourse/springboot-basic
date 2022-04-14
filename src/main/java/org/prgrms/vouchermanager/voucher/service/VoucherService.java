package org.prgrms.vouchermanager.voucher.service;

import org.prgrms.vouchermanager.voucher.domain.Voucher;

import java.util.UUID;

/**
 * Voucher에 관한 서비스
 */
public interface VoucherService {
    void createVoucher(String type, Long amount);

    String allVouchersToString();

    Voucher findVoucher(UUID voucherId);

    void useVoucher(Voucher voucher);
}

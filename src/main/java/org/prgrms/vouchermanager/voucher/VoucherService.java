package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

/**
 * Voucher에 관한 서비스
 */
public interface VoucherService {
    void createVoucher(VoucherType type, Long amount);
    String allVouchersToString();
    Voucher findVoucher(UUID voucherId);
    void useVoucher(Voucher voucher);
}

package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

/**
 * Voucher에 관한 서비스
 */
public interface VoucherService {
    Voucher createVoucher(VoucherType type, long amount);
    String allVouchersToString();
    Voucher getVoucher(UUID voucherId);
    void useVoucher(Voucher voucher);
}

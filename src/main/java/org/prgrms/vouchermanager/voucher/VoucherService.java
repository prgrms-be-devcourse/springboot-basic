package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

public interface VoucherService {
    Voucher createVoucher(String type, long amount);
    String getVouchersLists();
    Voucher getVoucher(UUID voucherId);
    void useVoucher(Voucher voucher);
}

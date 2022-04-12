package org.prgrms.voucherapp.engine;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    ArrayList<Voucher> getVoucherAll();
}

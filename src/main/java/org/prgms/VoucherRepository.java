package org.prgms;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId); //Optional : VoucherId가 없을 수도 있다.
}

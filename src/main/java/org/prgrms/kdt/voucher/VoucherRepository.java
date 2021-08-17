package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 9:09 오후
 */
public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
}

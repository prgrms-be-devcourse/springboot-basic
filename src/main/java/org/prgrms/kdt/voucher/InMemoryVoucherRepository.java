package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 1:31 오전
 */
public class InMemoryVoucherRepository implements VoucherRepository {

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }
}

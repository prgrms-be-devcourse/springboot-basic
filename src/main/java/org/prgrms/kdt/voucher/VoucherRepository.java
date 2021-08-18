package org.prgrms.kdt.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 9:09 오후
 */
public interface VoucherRepository {

    void save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    Map<UUID, Voucher> findAll();

}

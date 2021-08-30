package org.prgrms.kdt.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:40 오후
 */
public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    void deleteById(UUID voucherId);

    void deleteAll();

    int count();

}

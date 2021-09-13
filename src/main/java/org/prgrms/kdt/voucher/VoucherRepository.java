package org.prgrms.kdt.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:40 오후
 */
public interface VoucherRepository {

    void insert(Voucher voucher);

    void update(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    List<Voucher> findVouchersByCustomerId(UUID customerId);

    void deleteById(UUID voucherId);

    void deleteAll();

    int count();

}

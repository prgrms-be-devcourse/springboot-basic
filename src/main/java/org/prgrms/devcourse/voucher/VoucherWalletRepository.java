package org.prgrms.devcourse.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherWalletRepository {
    VoucherUseInfo insert(VoucherUseInfo voucherUseInfo);
    Optional<VoucherUseInfo> findOne(UUID voucherUseInfoId);
    List<VoucherUseInfo> findById(UUID customerId);
    List<VoucherUseInfo> findByEmail(String email);
    UUID delete(UUID voucherUseInfoId);
    VoucherUseInfo update(VoucherUseInfo voucherUseInfo);
    void deleteAll();
}

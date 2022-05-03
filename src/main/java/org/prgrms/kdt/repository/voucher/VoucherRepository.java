package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.Vouchers;
import org.prgrms.kdt.model.voucher.VoucherMap;


import java.util.*;

public interface VoucherRepository {
    Optional<Voucher> insertVoucher(Voucher voucher);

    VoucherMap getVoucherList();

    void deleteVoucherById(UUID voucherId);

    Optional<Voucher> getByVoucherId(UUID voucherId);

    Vouchers getVoucherListOwnerIdIsEmpty();

    Optional<Voucher> updateVoucherOwner(UUID voucherId, UUID customerId);

    Optional<Voucher> getVoucherNotProvided(UUID voucherId);

    void deleteAllVouchers();

    Vouchers getVoucherListByVoucherType(int voucherType);

    Vouchers getVoucherListByCreatedFromToDate(String fromDate, String toDate);
}

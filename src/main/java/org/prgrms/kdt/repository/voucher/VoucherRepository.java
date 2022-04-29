package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherList;
import org.prgrms.kdt.model.voucher.VoucherMap;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public interface VoucherRepository {
    Optional<Voucher> insertVoucher(Voucher voucher);

    VoucherMap getVoucherList();

    void deleteVoucherById(UUID voucherId);

    Optional<Voucher> getByVoucherId(UUID voucherId);

    VoucherList getVoucherListOwnerIdIsEmpty();

    Optional<Voucher> updateVoucherOwner(UUID voucherId, UUID customerId);

    Optional<Voucher> getVoucherNotProvided(UUID voucherId);

    void deleteAllVouchers();

    VoucherList getVoucherListByVoucherType(int voucherType);

    VoucherList getVoucherListByCreatedFromToDate(String fromDate, String toDate);
}

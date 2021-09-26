package org.prgrms.kdt.voucher.repository;

import java.util.*;
import org.prgrms.kdt.voucher.controller.VoucherSearch;
import org.prgrms.kdt.voucher.model.Voucher;


public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAllVoucher();

    Voucher updateType(Voucher voucher);

    void deleteAll();

    List<Voucher> findByCustomerId(UUID customerId);

    void deleteById(UUID voucherId);

    List<Voucher> findFilteredVouchers(VoucherSearch voucherSearch);
}


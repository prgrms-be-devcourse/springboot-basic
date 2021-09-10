package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.Voucher;

import java.util.*;


public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAllVoucher();

    Voucher updateType(Voucher voucher);

    void deleteAll();

}

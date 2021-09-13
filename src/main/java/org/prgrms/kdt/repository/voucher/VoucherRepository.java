package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;

import java.util.*;


public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAllVoucher();

    Voucher updateType(Voucher voucher);

    void deleteAll();

    List<Voucher> findByCustomerId(UUID customerId);

}

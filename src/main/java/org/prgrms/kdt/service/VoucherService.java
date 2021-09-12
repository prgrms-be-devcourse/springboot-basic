package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherService {

    Optional<Voucher> findByVoucherId(Long voucherId);

    void useVoucher(Voucher voucher);

    Voucher create(String voucherType);

    List<Voucher> list();

    Voucher allocate(Long voucherId, Long customerId);

}

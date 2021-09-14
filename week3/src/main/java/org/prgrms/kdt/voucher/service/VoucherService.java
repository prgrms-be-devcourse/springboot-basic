package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {

    void create(Voucher voucher);

    List<Voucher> list();

    void create(String voucherType, Long amount);

    Optional<Voucher> getVoucher(UUID voucherId);

    void deleteById(UUID voucherId);
}

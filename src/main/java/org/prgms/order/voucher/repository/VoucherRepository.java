package org.prgms.order.voucher.repository;

import org.prgms.order.voucher.entity.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    List<Voucher> findAllVoucher();

    String getVoucherInfoById(UUID voucherId);
}

package org.prgrms.vouchermanagement.service;

import org.prgrms.vouchermanagement.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher findVoucher(UUID voucherId);

    List<Voucher> findVouchers();

    UUID create(Long discountValue);
}

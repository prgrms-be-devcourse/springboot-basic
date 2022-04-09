package org.prgrms.kdt.service;

import org.prgrms.kdt.model.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherService {

    Voucher createVoucher();

    Optional<List<Voucher>> findAllVoucher();

}

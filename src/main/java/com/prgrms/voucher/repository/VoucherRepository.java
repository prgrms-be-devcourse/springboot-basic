package com.prgrms.voucher.repository;

import com.prgrms.voucher.model.voucher.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VoucherRepository {

    Optional<Voucher> findById(String voucherId);

    String deleteById(String voucherId);

    Voucher insert(Voucher voucher);

    Vouchers getAllVoucher(VoucherType voucherType, LocalDateTime startCreatedAt);

}

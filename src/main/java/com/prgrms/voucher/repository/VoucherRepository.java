package com.prgrms.voucher.repository;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VoucherRepository {

    Optional<Voucher> findById(int voucherId);

    int deleteById(int voucherId);

    Voucher insert(Voucher voucher);

    Vouchers getAllVoucher(VoucherType voucherType, LocalDateTime startCreatedAt);

}

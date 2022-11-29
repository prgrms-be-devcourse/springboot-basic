package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByType(VoucherType voucherType);

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);

}

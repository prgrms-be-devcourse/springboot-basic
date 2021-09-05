package org.programmers.voucher.repository;

import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    void update(VoucherType voucherType, long value, long changeValue);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Optional<Voucher> findByVoucherTypeAndVoucherValue(VoucherType voucherType, Long value);

    void deleteByVoucherId(UUID voucherId);

    void deleteByVoucherTypeAndValue(VoucherType voucherType, long value);

    void deleteAll();
}

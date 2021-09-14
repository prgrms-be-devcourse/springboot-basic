package org.programmers.voucher.repository;

import org.programmers.voucher.model.VoucherBase;
import org.programmers.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void create(VoucherBase voucherBase);

    void update(VoucherType voucherType, long value, long changeValue);

    void update(UUID voucherId,VoucherType voucherType, long changeValue);

    List<VoucherBase> findAll();

    List<VoucherBase> findAllByVoucherType(VoucherType voucherType);

    Optional<VoucherBase> findById(UUID voucherId);

    Optional<VoucherBase> findByVoucherTypeAndVoucherValue(VoucherType voucherType, Long value);

    void deleteByVoucherId(UUID voucherId);

    void deleteByVoucherTypeAndValue(VoucherType voucherType, long value);

    void deleteAll();
}

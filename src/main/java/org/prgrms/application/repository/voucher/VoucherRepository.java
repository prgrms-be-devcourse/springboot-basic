package org.prgrms.application.repository.voucher;

import org.prgrms.application.domain.voucher.VoucherEntity;
import org.prgrms.application.domain.voucher.VoucherType;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    VoucherEntity insert(VoucherEntity voucherEntity);

    VoucherEntity update(VoucherEntity voucherEntity);

    List<VoucherEntity> findAll();

    Optional<VoucherEntity> findById(Long voucherId);

    Optional<List<VoucherEntity>> findByType(VoucherType voucherType);

    void deleteAll();
}

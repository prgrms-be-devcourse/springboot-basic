package org.prgrms.application.repository.voucher;

import org.prgrms.application.entity.VoucherEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository {

    VoucherEntity insert(VoucherEntity voucherEntity);

    VoucherEntity update(VoucherEntity voucherEntity);

    List<VoucherEntity> findAll();

    Optional<VoucherEntity> findById(Long voucherId);

    List<VoucherEntity> findByType(String voucherType);

    void deleteAll();
}

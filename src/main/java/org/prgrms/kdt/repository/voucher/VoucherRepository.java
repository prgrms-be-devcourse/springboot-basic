package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.entity.VoucherEntity;

import java.util.*;

public interface VoucherRepository {
    VoucherEntity insert(VoucherEntity voucherEntity);
    VoucherEntity findById(Long voucherId);
    List<VoucherEntity> findAll();

    void deleteById(Long voucherId);
}

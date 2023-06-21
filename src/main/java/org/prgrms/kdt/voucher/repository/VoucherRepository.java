package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.*;

public interface VoucherRepository {

    public Voucher insert(Voucher voucher);
    public Optional<Voucher> findById(UUID voucherId);
    public List<Voucher> findAll();
}

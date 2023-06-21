package org.prgrms.kdt.repository;

import org.prgrms.kdt.Voucher;

import java.util.*;

public interface VoucherRepository {

    public Voucher insert(Voucher voucher);
    public Optional<Voucher> findById(UUID voucherId);
    public List<Voucher> findAll();
}

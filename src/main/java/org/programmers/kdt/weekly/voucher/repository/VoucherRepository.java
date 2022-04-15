package org.programmers.kdt.weekly.voucher.repository;

import java.util.List;
import org.programmers.kdt.weekly.voucher.model.Voucher;

public interface VoucherRepository {

    void insert(Voucher voucher);

    int count();

    List<Voucher> findAll();
}

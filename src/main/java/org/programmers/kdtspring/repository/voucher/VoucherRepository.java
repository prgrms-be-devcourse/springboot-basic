package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();

}

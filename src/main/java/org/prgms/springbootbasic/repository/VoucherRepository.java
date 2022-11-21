package org.prgms.springbootbasic.repository;


import org.prgms.springbootbasic.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository {
    List<Voucher> findAll();
    Voucher insert(Voucher voucher);
}

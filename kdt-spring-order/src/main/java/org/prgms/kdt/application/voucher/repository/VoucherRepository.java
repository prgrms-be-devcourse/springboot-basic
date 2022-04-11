package org.prgms.kdt.application.voucher.repository;

import org.prgms.kdt.application.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    UUID save(Voucher voucher);
    List<Voucher> findAll();
}

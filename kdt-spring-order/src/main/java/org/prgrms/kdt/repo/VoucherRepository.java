package org.prgrms.kdt.repo;

import org.prgrms.kdt.configure.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    void save(Voucher voucher);

}

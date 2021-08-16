package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    //Optioanl로 없을수도 있는 voucher에 대해 NPE방지
    Optional<Voucher> findById(UUID voucherId);

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

}

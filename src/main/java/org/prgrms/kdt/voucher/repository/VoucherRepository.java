package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    //Optioanl로 없을수도 있는 voucher에 대해 NPE방지
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

}

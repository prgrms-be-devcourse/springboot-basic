package org.prgrms.kdt.Repository;

import org.prgrms.kdt.Model.Voucher;
import org.prgrms.kdt.TypeStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    //id에 대한 정보가 없을 수 도 있기 때문에 optional을 사용하여 만들었다
    Optional<Voucher> findById(UUID voucherId);
    Voucher save(Voucher voucher);
    List<Voucher> findAllById();

}
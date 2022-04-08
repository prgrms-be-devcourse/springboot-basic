package org.prgrms.vouchermanager.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    // TODO : 강의 예제에서 Voucher 반환하도록 작성하셔서 일단 반환하도록 작성했습니다. 후에 사용안하면 변경
    Voucher insert(Voucher voucher);
    List<Voucher> getAll();
}

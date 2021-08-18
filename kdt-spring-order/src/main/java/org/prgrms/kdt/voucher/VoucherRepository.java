package org.prgrms.kdt.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> getVoucherList(); // 저장된 voucher list를 불러 오기 위해서 추˜
    Voucher insert(Voucher voucher); // voucher를 만들고 저장하기 위해서 추가
}

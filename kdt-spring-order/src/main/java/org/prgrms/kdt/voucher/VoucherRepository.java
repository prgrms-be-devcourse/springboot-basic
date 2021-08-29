package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);
    Map<UUID, Voucher> getVoucherList(); // 저장된 voucher list를 불러 오기 위해서 추˜
    Voucher insert(Voucher voucher); // voucher를 만들고 저장하기 위해서 추가
}

package org.prgrms.kdt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    public Optional<Voucher> findById(UUID voucherId);
    List<Voucher> getVoucherList(); // 저장된 voucher list를 불러 오기 위해서 추가
    public void insert(Voucher voucher); // voucher를 만들고 저장하기 위해서 추가
}

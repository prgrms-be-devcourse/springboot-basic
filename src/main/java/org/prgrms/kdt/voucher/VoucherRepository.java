package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository { // 여러가지 DBMS와 소통하기 위해 INTERFACE로 정의
    public Optional<Voucher> findById(UUID voucherId);
}

package org.prgms.order.voucher;

import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository { //db와 연결하는 객체는 어떤 db 종류와 연결되는지에 대한 내용을 모르기 때문에 repository는 interface로 정의함
    Optional<Voucher> findById(UUID voucherId);
        //optional을 쓴 이유는 코드상으로 voucherId가 없을수도 있다는 것을 표현하는 것이다.
    Voucher insert(Voucher voucher);
}

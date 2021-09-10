package org.programmers.applicationcontext.voucher;

import java.util.Optional;
import java.util.UUID;

/*데이터베이스의 종류에 따라 VoucherRepository(바우쳐 저장소)가 변할 수 있으므로 클래스말고 인터페이스로 생성한다*/
public interface VoucherRepository {

    /* voucherId를 통해 바우쳐를 조회하는 메소드지만 바우쳐 데이터가 비어있을 수도 있으므로 Optional해준다*/
    Optional<Voucher> findById(UUID voucherId);

    // void insert(Voucher voucher);
}

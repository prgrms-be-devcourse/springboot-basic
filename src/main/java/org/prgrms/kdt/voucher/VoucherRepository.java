package org.prgrms.kdt.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//jdbc뿐만 아니라 다양한 데이터 베이스를 쓸수도 있기 때문에 interface처리
public interface VoucherRepository {
    // 해당 바우처아이디가 데이터베이스에 없을 수 있기 때문에 Optional처리
    Optional<Voucher> findById(UUID voucherId);
    // 레파지토리에 대한 구현체를 별도로 가져가고 관계 설정을 OrderContext에서 맺어준다.

    // 실제 구현을 위해서 실제 바우처를 추가하는 insert 메소드 생성
    Voucher insert(Voucher voucher);

    Map<UUID, Voucher> findAll();
}

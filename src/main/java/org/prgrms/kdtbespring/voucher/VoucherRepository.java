package org.prgrms.kdtbespring.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// repository에서 가져오는 데이터가 rdbms,nosqlDB,documentDB 등 다양한 DB 에서 가져올 수 있다
// 그래서 어떻게 보관하는지에 대한 내용이 바뀔 수 있기 때문에 인터페이스로 작성하고 실제 상황에 맞춰 구현을 한다
// 그리고 구현체에 맞는 관계를 OrderContext에 가져가게 한다.
public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher insert(Voucher voucher);
}

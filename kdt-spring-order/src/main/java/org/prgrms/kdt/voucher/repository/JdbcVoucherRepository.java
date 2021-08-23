package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository //컴포넌트 스캔의 대상이됨
@Qualifier("jdbc")//Qualifier말고 프로파일로 지정해서 사용할 수도 있음
//@Profile("dev")
public class JdbcVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }

    @Override
    public Map<UUID, Voucher> returnAll() {
        return null;
    }
}

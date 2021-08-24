package org.prgrms.kdt.voucher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 1:31 오전
 */
@Repository
@Profile("dev")
public class InMemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return vouchers.get(voucher.getVoucherId());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(vouchers.get(voucherId));
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return Collections.unmodifiableMap(vouchers);
    }

}

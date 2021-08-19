package programmers.org.kdt.engine.voucher;

import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean,
    DisposableBean {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return Optional.of(voucher);
    }

    @Override
    public Set<Map.Entry<UUID, Voucher>> getAllEntry() {
        return storage.entrySet();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet called!");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("PreDestroy called!");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Destroy called!");
    }
}

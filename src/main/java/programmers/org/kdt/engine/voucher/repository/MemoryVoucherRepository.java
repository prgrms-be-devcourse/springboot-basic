package programmers.org.kdt.engine.voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import programmers.org.kdt.aop.TrackTime;
import programmers.org.kdt.engine.voucher.type.Voucher;

@Repository
//@Primary
//@Qualifier("memory")
@Profile({"dev", "test"})
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    @TrackTime
    public Optional<Voucher> insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return Optional.of(voucher);
    }

    @Override
    public Set<Map.Entry<UUID, Voucher>> getAllEntry() {
        return storage.entrySet();
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

//    @PostConstruct
//    public void postConstruct() {
//        System.out.println("postConstruct called!");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("afterPropertiesSet called!");
//    }
//
//    @PreDestroy
//    public void preDestroy(){
//        System.out.println("PreDestroy called!");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("Destroy called!");
//    }
}

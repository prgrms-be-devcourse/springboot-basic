package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local", "default", "test"})
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> find() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (storage.containsKey(voucher.getVoucherId())) {
            throw new IllegalArgumentException("중복된 Key 값을 가진 voucher 입니다.");
        }
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct Called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet Called!");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("preDestroy Called!");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy Called!");
    }
}

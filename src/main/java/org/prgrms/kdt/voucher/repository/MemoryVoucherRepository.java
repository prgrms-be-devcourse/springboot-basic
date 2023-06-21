package org.prgrms.kdt.voucher.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("memory")
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();



    @Override
    public Voucher insert(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstrict called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet called!");

    }

    @PreDestroy
    public void preDestroy() throws Exception {
        System.out.println("predestroy called!");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy called!");
    }
}

package org.prgrms.kdtspringdemo.voucher;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
@Primary
@Qualifier("memory")
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Stream<Voucher> findAll() {
        return storage.values().stream();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("[MemoryVoucherRepository]postConstruct called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[MemoryVoucherRepository]afterPropertiesSet called!");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("[MemoryVoucherRepository]preDestroy called!");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("[MemoryVoucherRepository]destroy called!");
    }
}
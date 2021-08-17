package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("memory")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Profile("development")
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Voucher save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("MemoryVoucherRepository.postConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MemoryVoucherRepository.afterPropertiesSet");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("MemoryVoucherRepository.preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("MemoryVoucherRepository.destroy");
    }
}

package org.prgrms.kdtbespring.voucher;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
 * 으로 Bean의 스코프를 변경할 수 있음. PROTOTYPE = 다른 객체 생성, SINGLETON = 하나의 객체만 생성
 */
@Repository
@Qualifier("memory")
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    // Thread safety 하게 처리하기 위한 Concurrent hashmap 사용
    private final Map<UUID,Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(storage.get(voucherId));
        return voucher;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("postConstruct called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet called!");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("preDestroy called!");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy called!");
    }
}

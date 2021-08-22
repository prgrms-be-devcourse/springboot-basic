package org.prgrms.kdtspringorder.voucher;

import org.prgrms.kdtspringorder.voucher.Voucher;
import org.prgrms.kdtspringorder.voucher.VoucherRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository//컴포넌트 스캔대상

@Qualifier("memory")
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId){
        return Optional.ofNullable(storage.get(voucherId));
    }
    @Override
    public Voucher insert(Voucher voucher){
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }
    @PostConstruct
    public void postConstruct(){
        System.out.println("postConstruct called!");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertieSet called!");
    }
    @PreDestroy
    public void predestroy() throws Exception {
        System.out.println("preDestroy called!");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy called!");
    }
}



package org.prgrms.kdt.voucher;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
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


@Repository
//@Primary //우선순위로 이 bean을 주입함
@Qualifier("memory") // 사용할때 해당 키워드를 이용해서 주입가능
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)// scope을 통해서 프로토타입(SCOPE_SINGLETON)으로 정의 가능 (여러개 객체생성가능)
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {
    //hash map을 통한 메모리관리
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId)); //null인경우 empty반환
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public  Map<UUID,Voucher> returnAll() {
        return storage;
    }


    //3일차 수업 : 빈 생명주기
    @PostConstruct
    public void postConstruct(){
        System.out.println("postConstruct called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet called!");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destory called!");
    }

    @PreDestroy
    public void preDestory(){
        System.out.println("preDestory called!");
    }
}

package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.Voucher;
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

// 두개의 VoucherRepository가 등록되는데, VoucherService에서 생성자 주입을 통해서 Repository가 주입된다.
// 그러면 2개의 인스턴스가 있으니까 VoucherRepository에 매칭되는게 2개니까 어떤 Bean이 자동으로 주입되는건지 선택해야합니다.
@Repository
// @Primary // 생성자 주입시 매칭되는게 여러개일 경우 얘가 우선시로 주입되어야해
@Qualifier("memory")// 용도를 구별
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) // value로 scope에 어떠한 스콥을 가질 수 있는지 설정 해야합니다. (prototype은 singleTon이 아닌 새로운 객체를 생성하여 보여준다.)
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    private final Map<UUID, Voucher> storage = new HashMap<>();

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
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    // 생성
    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct Called!");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet Called!");

    }

    // 삭제
    @PreDestroy
    public void preDestroy() {
        System.out.println("preDestroy Called!");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy Called!");

    }
}

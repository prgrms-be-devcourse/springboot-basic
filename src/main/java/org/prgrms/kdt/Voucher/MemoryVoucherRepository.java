package org.prgrms.kdt.Voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Qualifier("memory")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)

public class MemoryVoucherRepository implements VoucherRepository{
    //쓰레드 -safe하게 하기 위해서
    private final Map<UUID,Voucher> storage=new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher) {
        //null인 경우 empty가 반환됨
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return  new ArrayList<>(storage.values());
    }
}

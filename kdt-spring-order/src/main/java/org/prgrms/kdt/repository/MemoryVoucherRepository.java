package org.prgrms.kdt.repository;


import org.prgrms.kdt.domain.Voucher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//3일차 수업 내용에서 추가
@Repository
public class MemoryVoucherRepository implements VoucherRepository { //,InitializingBean, DisposableBean

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll()
    {
        return storage.values().stream().collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Voucher insert2(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

//    @PostConstruct
//    public void postConstruct(){
//        System.out.println("postConstruct called!!");
//    }
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("afterPropertiesSet called!!");
//    }
//
//    @PreDestroy
//    public void preDestroy(){
//        System.out.println("preDestroy called!!!");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("destroy called!!!");
//    }
}

package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.exception.EmptyRepositoryException;
import com.prgrms.voucher_manager.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private static final  Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();


    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        logger.info("MemoryVoucherRepository - save ");
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        if(storage.isEmpty()) throw new EmptyRepositoryException("MemoryVoucherRepository 가 비어있습니다.");
        logger.info("MemoryVoucherRepository - findAll");
        storage.forEach(((uuid, voucher) -> {
            vouchers.add(voucher);
            System.out.println(voucher.toString());
        }));
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findByType(String type) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public Voucher delete(Voucher voucher) {
        storage.remove(voucher.getVoucherId());
        return voucher;
    }

    @Override
    public int count() {
        return 0;
    }

}


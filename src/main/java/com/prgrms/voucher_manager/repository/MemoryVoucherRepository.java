package com.prgrms.voucher_manager.repository;

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
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherID(), voucher);
        logger.info("MemoryVoucherRepository - save ");
    }

    @Override
    public void findAll() {
        logger.info("MemoryVoucherRepository - findAll");
        storage.forEach(((uuid, voucher) -> {
            System.out.println(voucher.getInfo());
        }));
    }

    @Override
    public int getRepositorySize() {
        return storage.size();
    }
}


package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);


    @Override
    public void save(Voucher voucher) {
        logger.info("[MemoryVoucherRepository] save() called");
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("[MemoryVoucherRepository] findAll() called");
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        return null;
    }

    @Override
    public void deleteOne(Voucher voucher) {

    }
}

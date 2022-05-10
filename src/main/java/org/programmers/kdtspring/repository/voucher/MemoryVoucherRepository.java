package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.exception.NotAvailableMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void insert(Voucher voucher) {
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
    public void updateCustomerId(Voucher voucher) {
        throw new NotAvailableMethod("이 메서드는 지원하지 않습니다.");
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        Iterator<UUID> keys = storage.keySet().iterator();
        List<Voucher> vouchers = new ArrayList<>();
        while (keys.hasNext()) {
            UUID key = keys.next();
            vouchers.add(storage.get(key));
        }
        return vouchers;
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        throw new NotAvailableMethod("이 메서드는 지원하지 않습니다.");
    }

    @Override
    public void deleteOne(Voucher voucher) {
        storage.remove(voucher.getVoucherId());
    }
}
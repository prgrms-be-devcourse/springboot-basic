package org.prgrms.springbootbasic.repository.voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"local", "default"})
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void insert(Voucher voucher) {
        logger.info("MemoryVoucherRepository.save() called");

        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("MemoryVoucherRepository.findAll() called");

        return new ArrayList<>(storage.values());
    }

    @Override
    public void removeAll() {
        storage.clear();
    }

    @Override
    public Voucher updateCustomerId(Voucher voucher) {
        throw new AssertionError("아직 개발 안함");
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new AssertionError("아직 개발 안함");
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        throw new AssertionError("아직 개발 안함");
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        throw new AssertionError("아직 개발 안함");
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        throw new AssertionError("아직 개발 안함");
    }

    @Override
    public List<Voucher> findByCreatedAt(LocalDateTime startTime, LocalDateTime endTime) {
        throw new AssertionError("아직 개발 안함");
    }
}

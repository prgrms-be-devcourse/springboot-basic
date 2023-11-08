package org.programmers.springorder.voucher.repository;

import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    //TODO: 구현 필요 기능(jdbc 우선)
    @Override
    public Voucher updateVoucherOwner(Voucher voucher, Customer customer) {
        voucher.updateOwner(customer);
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAllByCustomerId(Customer customer) {
        return storage.values()
                .stream()
                .filter(voucher -> voucher.comparingCustomer(customer.getCustomerId()))
                .toList();
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        storage.remove(voucher.getVoucherId());
    }

    @Override
    public List<Voucher> findAllByTimeLimit(LocalDateTime startedAt, LocalDateTime endedAt) {
        return storage.values()
                .stream()
                .filter(voucher -> voucher.voucherRange(startedAt, endedAt))
                .toList();
    }

    public void reset(){
        storage.clear();
    }

}

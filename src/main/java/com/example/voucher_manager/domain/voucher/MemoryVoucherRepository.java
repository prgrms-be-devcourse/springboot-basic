package com.example.voucher_manager.domain.voucher;

import com.example.voucher_manager.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> memory = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memory.values());
    }

    @Override
    public List<Voucher> findVoucherListByCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Voucher> findVoucherListByType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Voucher> findVoucherListByPeriods(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try {
            memory.put(voucher.getVoucherId(), voucher);
        } catch (RuntimeException e){
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    public void clear() {
        memory.clear();
    }

    @Override
    public void deleteVoucherByCustomer(Voucher voucher, Customer customer) {

    }

    @Override
    public boolean deleteVoucherById(UUID voucherId) {
        return false;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if (memory.containsKey(voucherId)) {
            return Optional.of(memory.get(voucherId));
        }
        return Optional.empty();
    }

    @Override
    public Voucher update(Voucher voucher) {
        if (memory.containsKey(voucher.getVoucherId())) {
            memory.put(voucher.getVoucherId(), voucher);
        }
        return voucher;
    }

}

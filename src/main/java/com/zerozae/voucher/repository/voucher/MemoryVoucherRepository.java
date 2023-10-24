package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, List<UUID>> customerVouchers;
    private final Map<UUID, Voucher> vouchers;

    public MemoryVoucherRepository() {
        this.customerVouchers = new ConcurrentHashMap<>();
        this.vouchers = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public void registerVoucher(UUID customerId, UUID voucherId) {
        customerVouchers
                .computeIfAbsent(customerId, k -> new ArrayList<>())
                .add(voucherId);
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return customerVouchers.getOrDefault(customerId, Collections.emptyList())
                .stream()
                .map(voucherId -> findById(voucherId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void removeVoucher(UUID voucherId) {
        customerVouchers.values().forEach(vouchers -> vouchers.remove(voucherId));
    }

    @Override
    public Optional<UUID> findVoucherOwner(UUID voucherId) {
        return customerVouchers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(voucherId))
                .map(Map.Entry::getKey)
                .findAny();
    }

    @Override
    public void deleteById(UUID voucherId) {
        vouchers.remove(voucherId);
        customerVouchers.values().forEach(customerVoucherList -> customerVoucherList.remove(voucherId));
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
        customerVouchers.clear();
    }

    @Override
    public void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = vouchers.get(voucherId);
        voucher.updateVoucherInfo(voucherUpdateRequest);
    }
}
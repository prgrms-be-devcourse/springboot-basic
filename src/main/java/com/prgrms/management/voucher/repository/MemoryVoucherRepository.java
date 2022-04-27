package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findAllByVoucherTypeOrCreatedAt(VoucherType voucherType, LocalDate date) {
        // TODO: 조건별 vouchers 반환하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UUID> findCustomerByVoucherType(VoucherType voucherType) {
        // TODO: voucherType 조건으로 vouchers 반환하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        // TODO: voucherById 반환하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateByCustomerId(UUID voucherId, UUID customerId) {
        // TODO: voucherId 검색 후 customerId 주입하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(UUID customerId) {
        // TODO: customerId로 삭제하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        // TODO: 전부 삭제하는 메서드
        throw new UnsupportedOperationException();
    }
}

